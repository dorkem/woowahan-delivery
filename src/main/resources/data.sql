INSERT INTO users (user_id, username, phone_number, email, password, login_type, user_type, created_at)
VALUES (1, '최재혁', '010-1234-5678', 'user@test.com', 'password123', 'KAKAO', 'CUSTOMER', NOW());

INSERT INTO store (store_id, owner_user_id, store_name, business_number, store_address, store_address_details, status, min_order_amount, base_delivery_fee, created_at)
VALUES (1, 1, 'BBQ', '123-45-67890', '서울특별시 강남구', '15층', 'OPEN', 15000, 3000, NOW());

INSERT INTO menu (menu_id, store_store_id, menu_name, menu_description, price, created_at)
VALUES (1, 1, '황금올리브 치킨', '후라이드 치킨', 23000, NOW());

INSERT INTO menu (menu_id, store_store_id, menu_name, menu_description, price, created_at)
VALUES (2, 1, '뿌링치즈볼', '쫀득쫀득 치즈볼', 6000, NOW());
