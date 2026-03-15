import pytest
import legacy_processor
import datetime

@pytest.fixture(autouse=True)
def clear_db():
    legacy_processor.db.clear()

@pytest.mark.parametrize("input_data,expected_amt", [
    ({'id': 'A', 'type': 1, 'amt': 100001}, 100001 * 0.9),  # type 1, 100,001원 -> 10% 할인
    ({'id': 'B', 'type': 1, 'amt': 100000}, 100000),        # type 1, 100,000원 -> 할인 없음
    ({'id': 'C', 'type': 2, 'amt': 50000}, 50000 * 0.85),   # type 2, 50,000원 -> 15% 할인
    ({'id': 'D', 'type': 2, 'amt': 200000}, 200000 * 0.85), # type 2, 200,000원 -> 15% 할인
])
def test_process_it_amt(input_data, expected_amt):
    legacy_processor.process_it([input_data])
    assert len(legacy_processor.db) == 1
    record = legacy_processor.db[0]
    assert record['id'] == input_data['id']
    assert pytest.approx(record['final_amt'], rel=1e-6) == expected_amt
    # 날짜 형식 확인
    assert isinstance(record['date'], str)
    assert len(record['date'].split('-')) == 3

def test_process_it_alert(monkeypatch):
    called = {}
    def fake_alert(id):
        called['id'] = id
    monkeypatch.setattr(legacy_processor, 'send_alert', fake_alert)
    # 50,000원 초과 케이스
    legacy_processor.process_it([{'id': 'E', 'type': 1, 'amt': 100001}])
    assert called['id'] == 'E'
    # 50,000원 이하 케이스
    called.clear()
    legacy_processor.process_it([{'id': 'F', 'type': 1, 'amt': 10000}])
    assert 'id' not in called
