`markdown
프로젝트 규칙


언어: 주석, 변수명 설명, Chat 설명 등은 모두 '한국어(Korean)'로 작성하세요.
네이밍 규칙: 모든 Python 변수 및 함수 이름은 'snake_case' 방식을 사용하세요.
예외 처리 강제 규정: 함수 내에 try-except 구문을 사용할 때, 에러 발생 시 항상 \logging.error("에러 메시지", exc_info=True)\ 방식으로 로그를 남겨야 합니다. Print 문을 쓰지 마세요.
타입 힌트: Python 코딩 시 모든 인자와 리턴 타입에 Type Hint를 명시하세요.
`