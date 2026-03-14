# 리스트의 평균을 계산하는 함수
from typing import List
import logging

def calculate_average(numbers: List[float]) -> float:
    try:
        if not numbers:
            raise ValueError("리스트에 요소가 없습니다.")
        return sum(numbers) / len(numbers)
    except Exception as e:
        logging.error("평균 계산 중 에러 발생", exc_info=True)
        raise
# 현재 날짜와 시간을 iso 8601 형식으로 반환하는 함수
from datetime import datetime

def get_current_iso_datetime():
    return datetime.now().isoformat()

# 두수를 더하는 함수
def add_numbers(a, b):
    return a + b

def subtract_numbers(a, b):
    return a - b

def multiply_numbers(a, b):
    return a * b

# 방법 1: 일반적인 for문을 사용한 소수 판별 입력 후 제안 수락.
def find_primes(n):
    primes = []
    for num in range(2, n + 1):
        is_prime = True
        for i in range(2, int(num**0.5) + 1):
            if num % i == 0:
                is_prime = False
                break
        if is_prime:
            primes.append(num)
    return primes

# 방법 2: 리스트 컴프리헨션을 활용한 소수 추출
def find_primes_list_comprehension(n):
    return [num for num in range(2, n + 1) if all(num % i != 0 for i in range(2, int(num**0.5) + 1))]

# 방법 3: 에라토스테네스의 체 알고리즘을 사용한 소수 판별
def sieve_of_eratosthenes(n):
    sieve = [True] * (n + 1)
    sieve[0] = sieve[1] = False
    for start in range(2, int(n**0.5) + 1):
        if sieve[start]:
            for multiple in range(start*start, n + 1, start):
                sieve[multiple] = False
    return [num for num, is_prime in enumerate(sieve) if is_prime]
