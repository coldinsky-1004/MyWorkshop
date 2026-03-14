def validate_password(password):
    # 1. 길이 체크 (8자 이상)
    if len(password) < 8:
        return False
    
    # 2. 숫자 포함 여부 체크
    if not any(char.isdigit() for char in password):
        return False
    
    # 3. 대문자 포함 여부 체크
    if not any(char.isupper() for char in password):
        return False
    
    return True
