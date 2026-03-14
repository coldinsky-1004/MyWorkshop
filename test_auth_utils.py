# 언어: 한국어
# 파일: test_auth_utils.py
# 테스트 프레임워크: pytest 사용
# validate_password 함수 테스트

import pytest
from auth_utils import validate_password

def test_validate_password_short_length():
    """8자 미만 비밀번호는 False를 반환해야 한다."""
    assert validate_password("Abc123") is False

def test_validate_password_no_digit():
    """숫자가 없는 비밀번호는 False를 반환해야 한다."""
    assert validate_password("Abcdefgh") is False

def test_validate_password_no_uppercase():
    """대문자가 없는 비밀번호는 False를 반환해야 한다."""
    assert validate_password("abcdefg1") is False

def test_validate_password_valid():
    """조건을 모두 만족하는 비밀번호는 True를 반환해야 한다."""
    assert validate_password("Abcdefg1") is True

def test_validate_password_only_digits():
    """숫자만 있고 대문자가 없는 비밀번호는 False를 반환해야 한다."""
    assert validate_password("12345678") is False

def test_validate_password_only_uppercase():
    """대문자만 있고 숫자가 없는 비밀번호는 False를 반환해야 한다."""
    assert validate_password("ABCDEFGH") is False

def test_validate_password_uppercase_and_digit():
    """대문자와 숫자가 모두 있지만 길이가 부족하면 False를 반환해야 한다."""
    assert validate_password("A1b2c3") is False