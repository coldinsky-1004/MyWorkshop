import logging
from datetime import datetime
from typing import Callable, List, Dict, Any, Protocol

# 알림 인터페이스 정의
class AlertSender(Protocol):
    def send_alert(self, user_id: int, amount: int) -> None:
        ...

class LegacyProcessor:
    """
    할인 규칙 및 데이터 저장을 담당하는 클래스
    """

    def __init__(self, alert_sender: AlertSender) -> None:
        # 데이터 저장 리스트
        self.db: List[Dict[str, Any]] = []
        # 알림 전송 객체
        self.alert_sender = alert_sender

    def process(self, user_id: int, amount: int, discount_type: int) -> None:
        """
        할인 규칙 적용 및 데이터 저장, 알림 전송 처리

        :param user_id: 사용자 ID
        :param amount: 결제 금액
        :param discount_type: 할인 타입 (1 또는 2)
        :return: None
        """
        try:
            # 할인 규칙 적용
            if discount_type == 1:
                if amount > 100_000:
                    final_amount = int(amount * 0.9)
                else:
                    final_amount = amount
            elif discount_type == 2:
                final_amount = int(amount * 0.85)
            else:
                final_amount = amount  # 할인 없음

            # 데이터 저장
            self.db.append({
                "id": user_id,
                "final_amt": final_amount,
                "date": datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            })

            # 알림 조건 처리
            if final_amount > 50_000:
                self.alert_sender.send_alert(user_id, final_amount)

        except Exception as e:
            logging.error("프로세스 처리 중 에러 발생", exc_info=True)

# 예시 알림 구현체
class DummyAlertSender:
    def send_alert(self, user_id: int, amount: int) -> None:
        # 실제 구현에서는 이메일, SMS 등 전송
        pass

# 사용 예시
# alert_sender = DummyAlertSender()
# processor = LegacyProcessor(alert_sender)
# processor.process(1, 120000, 1)
