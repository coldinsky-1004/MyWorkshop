class OrderProcessor:
    # 할인율 상수 선언
    DISCOUNT_RATES = {
        'VIP': 0.8,
        'NEW': 0.95
    }
    def process_order(self, order: dict) -> None:
        """
        주문 처리 메서드
        """
        try:
            self.validate_order(order)
        except Exception as e:
            import logging
            logging.error("주문 데이터 유효성 검사 실패", exc_info=True)
            raise
        
        # 2. 가격 계산 (Calculation) - 중복 제거
        total = self.calculate_total(order['items'])
        
        # 3. 할인 로직 (Dictionary Mapping 방식)
        discount_rate = self.DISCOUNT_RATES.get(order['type'], 1.0)
        total = total * discount_rate
        
        # 4. 결제 및 알림 (I/O)
        print(f"User {order['user_id']} paid {total}")
        # ... (DB 저장 및 이메일 로직 생략) ...
    def validate_order(self, order: dict) -> None:
        """
        주문 데이터의 유효성을 검사합니다.
        실패 시 ValueError 예외를 발생시킵니다.
        """
        if order.get('items') is None or len(order['items']) == 0:
            raise ValueError("주문 항목이 없습니다.")
        if order.get('user_id') is None:
            raise ValueError("사용자 정보가 없습니다.")

    def process_refund(self, refund):
        # 가격 계산 로직 중복 제거
        total = self.calculate_total(refund['items'])
        # ... 환불 전용 로직 수행 ...
        print(f"Refund of {total} processed")
    def calculate_total(self, items: list) -> float:
        """
        아이템 리스트를 받아 총 가격을 계산합니다.
        각 아이템은 price와 qty 키를 가져야 합니다.
        """
        total = 0.0
        for item in items:
            total += item['price'] * item['qty']
        return total
