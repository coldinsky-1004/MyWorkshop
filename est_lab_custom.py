from typing import List
import logging

def calculate_average(numbers: List[float]) -> float:
    """
    리스트(numbers)의 평균을 계산하여 반환합니다.

    인자:
        numbers (List[float]): 평균을 계산할 숫자 리스트

    반환:
        float: 리스트의 평균값

    예외:
        ValueError: 리스트가 비어 있을 경우 발생
        기타 Exception: 평균 계산 중 에러 발생 시 로깅 후 재발생

    사용 예시:
        calculate_average([1.0, 2.0, 3.0])  # 2.0 반환
    """
    try:
        if not numbers:
            raise ValueError("리스트에 요소가 없습니다.")
        return sum(numbers) / len(numbers)
    except Exception as e:
        logging.error("평균 계산 중 에러 발생", exc_info=True)
        raise

    