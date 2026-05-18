-- 1. 카테고리 데이터
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (1, '홈', 'home', 1);
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (2, '한식', 'korean', 2);
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (3, '분식', 'snack', 3);
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (4, '카페·디저트', 'cafe', 4);
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (5, '일식', 'japanese', 5);
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (6, '치킨', 'chicken', 6);
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (7, '피자', 'pizza', 7);
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (8, '양식', 'western', 8);
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (9, '중식', 'chinese', 9);
INSERT INTO category (category_id, category_name, slug, sort_order)
VALUES (10, '족발·보쌈', 'pork', 10);

-- 2. 유저 데이터 (modified_at 추가)
INSERT INTO users (user_id, username, phone_number, email, password, role, user_profile, created_at, modified_at)
VALUES (1, '김사장', '010-1111-1111', 'owner1@test.com', 'pass123', 'OWNER',
        'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/default_user.png',
        NOW(), NOW());

-- 3. 사장님 데이터
INSERT INTO owners (owner_id, user_id, owner_name, business_number, created_at, modified_at)
VALUES (1, 1, '김사장', '123-45-67890', NOW(), NOW());

-- 4. 식당 데이터
-- [치킨]
INSERT INTO stores (store_id, owner_id, category_id, thumbnail, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (1, 1, 6, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', 'BBQ 강남점', '123-45-67890', '서울시 강남구', '1층', 'OPEN', 15000, 3000, NOW(), NOW()),
       (2, 1, 6, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '교촌치킨 압구정점', '123-45-67891', '서울시 강남구', '102호', 'OPEN', 16000, 3000, NOW(), NOW()),
       (3, 1, 6, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', 'BHC 청담점', '123-45-67892', '서울시 강남구', '2층', 'OPEN', 15000, 2000, NOW(), NOW());

-- [일식]
INSERT INTO stores (store_id, owner_id, category_id, thumbnail, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (4, 1, 5, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '상무초밥 송파점', '123-45-67893', '서울시 송파구', '101호', 'OPEN', 20000, 4000, NOW(), NOW()),
       (5, 1, 5, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '홍대돈부리 신촌점', '123-45-67894', '서울시 서대문구', '1층', 'OPEN', 12000, 2500, NOW(), NOW());

-- [한식]
INSERT INTO stores (store_id, owner_id, category_id, thumbnail, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (6, 1, 2, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '김밥천국 강남본점', '123-45-67895', '서울시 강남구', '1층', 'OPEN', 10000, 2000, NOW(), NOW()),
       (7, 1, 2, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '큰맘할매순대국 역삼점', '123-45-67896', '서울시 강남구', '지하 1층', 'OPEN', 14000, 3000, NOW(), NOW()),
       (8, 1, 2, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '본죽 서초점', '123-45-67897', '서울시 서초구', '1층', 'OPEN', 12000, 3500, NOW(), NOW());

-- [분식]
INSERT INTO stores (store_id, owner_id, category_id, thumbnail, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (9, 1, 3, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '동대문엽기떡볶이 논현점', '123-45-67898', '서울시 강남구', '2층', 'OPEN', 14000, 3000, NOW(), NOW()),
       (10, 1, 3, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '신전떡볶이 삼성점', '123-45-67899', '서울시 강남구', '1층', 'OPEN', 13000, 2500, NOW(), NOW());

-- [카페·디저트]
INSERT INTO stores (store_id, owner_id, category_id, thumbnail, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (11, 1, 4, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '메가MGC커피 선릉점', '123-45-67900', '서울시 강남구', '1층', 'OPEN', 8000, 2000, NOW(), NOW()),
       (12, 1, 4, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '스타벅스 강남역점', '123-45-67901', '서울시 강남구', '1, 2층', 'OPEN', 15000, 3000, NOW(), NOW());

-- [피자]
INSERT INTO stores (store_id, owner_id, category_id, thumbnail, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (13, 1, 7, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '도미노피자 반포점', '123-45-67902', '서울시 서초구', '1층', 'OPEN', 20000, 0, NOW(), NOW()),
       (14, 1, 7, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '파파존스 서초점', '123-45-67903', '서울시 서초구', '1층', 'OPEN', 18000, 2000, NOW(), NOW());

-- [양식]
INSERT INTO stores (store_id, owner_id, category_id, thumbnail, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (15, 1, 8, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '아웃백스테이크하우스 강남점', '123-45-67904', '서울시 강남구', '3층', 'OPEN', 30000, 5000, NOW(), NOW()),
       (16, 1, 8, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '서가앤쿡 홍대점', '123-45-67905', '서울시 마포구', '2층', 'OPEN', 20000, 4000, NOW(), NOW());

-- [중식]
INSERT INTO stores (store_id, owner_id, category_id, thumbnail, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (17, 1, 9, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '홍콩반점0410 신사점', '123-45-67906', '서울시 강남구', '지하 1층', 'OPEN', 13000, 2500, NOW(), NOW()),
       (18, 1, 9, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '백종원의 짜장면', '123-45-67907', '서울시 종로구', '1층', 'OPEN', 12000, 2000, NOW(), NOW());

-- [족발·보쌈]
INSERT INTO stores (store_id, owner_id, category_id, thumbnail, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (19, 1, 10, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '가장맛있는족발 건대점', '123-45-67908', '서울시 광진구', '1층', 'OPEN', 25000, 3000, NOW(), NOW()),
       (20, 1, 10, 'https://woowahan-d.s3.ap-northeast-2.amazonaws.com/defaults/defult_store.png', '원할머니보쌈 종로점', '123-45-67909', '서울시 종로구', '2층', 'OPEN', 30000, 3000, NOW(), NOW());

-- 5. 메뉴 데이터
-- [BBQ 강남점 - store_id: 1]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (1, '황금올리브치킨', '바삭한 황금올리브 반마리', 10000, false, NOW(), NOW()),
       (1, '황금올리브치킨 한마리', '바삭한 황금올리브 한마리', 19000, false, NOW(), NOW()),
       (1, '양념치킨', '달콤한 양념 한마리', 18000, false, NOW(), NOW()),
       (1, '콜라 1.25L', '시원한 콜라', 3000, false, NOW(), NOW());

-- [교촌치킨 압구정점 - store_id: 2]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (2, '교촌오리지날', '교촌 시그니처 간장 치킨 한마리', 20000, false, NOW(), NOW()),
       (2, '허니콤보', '허니와 오리지날 반반', 21000, false, NOW(), NOW()),
       (2, '레드콤보', '레드와 오리지날 반반', 21000, false, NOW(), NOW()),
       (2, '치즈볼', '쫄깃한 치즈볼 12개', 5000, false, NOW(), NOW());

-- [BHC 청담점 - store_id: 3]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (3, '뿌링클', '치즈 시즈닝 뿌링클 한마리', 19000, false, NOW(), NOW()),
       (3, '맛초킹', '매콤한 맛초킹 한마리', 18000, false, NOW(), NOW()),
       (3, '골드킹', '황금 간장 골드킹 한마리', 20000, false, NOW(), NOW()),
       (3, '해쉬브라운', '바삭한 해쉬브라운 4개', 4000, false, NOW(), NOW());

-- [상무초밥 송파점 - store_id: 4]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (4, '런치 특선 세트', '초밥 10피스 + 미소국', 15000, false, NOW(), NOW()),
       (4, '프리미엄 초밥 20피스', '신선한 제철 생선 초밥', 28000, false, NOW(), NOW()),
       (4, '연어 초밥 8피스', '노르웨이 생연어 초밥', 16000, false, NOW(), NOW()),
       (4, '참치 대뱃살 초밥 4피스', '최상급 참치 대뱃살', 18000, false, NOW(), NOW());

-- [홍대돈부리 신촌점 - store_id: 5]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (5, '규동', '소고기 덮밥 + 미소국', 9000, false, NOW(), NOW()),
       (5, '가라아게동', '일본식 닭튀김 덮밥', 9500, false, NOW(), NOW()),
       (5, '연어동', '신선한 연어 덮밥', 11000, false, NOW(), NOW()),
       (5, '온센타마고', '반숙 온천란', 1500, false, NOW(), NOW());

-- [김밥천국 강남본점 - store_id: 6]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (6, '참치김밥', '참치가 가득한 김밥 한줄', 4500, false, NOW(), NOW()),
       (6, '치즈김밥', '고소한 치즈 김밥 한줄', 4000, false, NOW(), NOW()),
       (6, '라볶이', '라면+떡볶이 세트', 7000, false, NOW(), NOW()),
       (6, '돈까스', '바삭한 돈까스 정식', 8000, false, NOW(), NOW());

-- [큰맘할매순대국 역삼점 - store_id: 7]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (7, '순대국밥', '진한 국물 순대국밥', 9000, false, NOW(), NOW()),
       (7, '모듬순대국밥', '순대+내장 모듬 국밥', 10000, false, NOW(), NOW()),
       (7, '순대 한접시', '따뜻한 순대 한접시', 7000, false, NOW(), NOW()),
       (7, '수육 소', '보쌈용 수육 소자', 14000, false, NOW(), NOW());

-- [본죽 서초점 - store_id: 8]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (8, '전복죽', '국내산 전복 죽', 13000, false, NOW(), NOW()),
       (8, '소고기죽', '한우 소고기 죽', 11000, false, NOW(), NOW()),
       (8, '야채죽', '신선한 야채 죽', 8000, false, NOW(), NOW()),
       (8, '참치죽', '참치 야채 죽', 9000, false, NOW(), NOW());

-- [동대문엽기떡볶이 논현점 - store_id: 9]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (9, '엽기떡볶이 소', '매운 엽기 떡볶이 소자', 14000, false, NOW(), NOW()),
       (9, '로제떡볶이 소', '크리미한 로제 소자', 15000, false, NOW(), NOW()),
       (9, '치즈떡볶이 소', '고소한 치즈 소자', 15000, false, NOW(), NOW()),
       (9, '튀김 5종 세트', '오징어·새우·야채튀김', 8000, false, NOW(), NOW());

-- [신전떡볶이 삼성점 - store_id: 10]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (10, '국물떡볶이 1인', '얼큰한 국물 떡볶이 1인분', 6000, false, NOW(), NOW()),
       (10, '국물떡볶이 2인', '얼큰한 국물 떡볶이 2인분', 11000, false, NOW(), NOW()),
       (10, '순대 추가', '쫄깃한 순대 추가', 3000, false, NOW(), NOW()),
       (10, '라면사리', '쫄깃한 라면사리', 1000, false, NOW(), NOW());

-- [메가MGC커피 선릉점 - store_id: 11]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (11, '아메리카노 ICE', '진한 에스프레소 아이스 아메리카노', 2000, false, NOW(), NOW()),
       (11, '아메리카노 HOT', '따뜻한 아메리카노', 2000, false, NOW(), NOW()),
       (11, '카페라떼 ICE', '부드러운 아이스 라떼', 2500, false, NOW(), NOW()),
       (11, '크림라떼', '달콤한 크림 라떼', 3500, false, NOW(), NOW());

-- [스타벅스 강남역점 - store_id: 12]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (12, '아이스 아메리카노', '스타벅스 아이스 아메리카노 Tall', 4500, false, NOW(), NOW()),
       (12, '카페 라떼', '부드러운 카페 라떼 Tall', 5000, false, NOW(), NOW()),
       (12, '자바 칩 프라푸치노', '초콜릿 자바 칩 프라푸치노', 6300, false, NOW(), NOW()),
       (12, '딸기 딜라이트 요거트', '상큼한 딸기 요거트 블렌디드', 6800, false, NOW(), NOW());

-- [도미노피자 반포점 - store_id: 13]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (13, '슈퍼시드 L', '도미노 시그니처 슈퍼시드 L', 25900, false, NOW(), NOW()),
       (13, '포테이토 L', '감자가 듬뿍 포테이토 L', 24900, false, NOW(), NOW()),
       (13, '불고기 L', '달콤 불고기 피자 L', 25900, false, NOW(), NOW()),
       (13, '치킨스테이크 사이드', '바삭한 치킨스테이크 5조각', 6900, false, NOW(), NOW());

-- [파파존스 서초점 - store_id: 14]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (14, '가든 파티 L', '신선 야채 듬뿍 L', 23900, false, NOW(), NOW()),
       (14, '슈프림 L', '풍성한 토핑 슈프림 L', 26900, false, NOW(), NOW()),
       (14, '바베큐 치킨 L', '훈제 바베큐 치킨 L', 25900, false, NOW(), NOW()),
       (14, '치즈스틱 5개', '쫄깃한 치즈스틱', 5900, false, NOW(), NOW());

-- [아웃백 강남점 - store_id: 15]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (15, '시즐러 스테이크 200g', '아웃백 시그니처 시즐러', 30900, false, NOW(), NOW()),
       (15, '부시맨 브레드', '갓 구운 허니버터 브레드', 5900, false, NOW(), NOW()),
       (15, '블루밍 어니언', '아웃백 시그니처 어니언', 11900, false, NOW(), NOW()),
       (15, '치킨 파마산', '크리스피 치킨 파마산', 23900, false, NOW(), NOW());

-- [서가앤쿡 홍대점 - store_id: 16]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (16, '투움바 파스타', '크리미한 투움바 파스타', 15900, false, NOW(), NOW()),
       (16, '리가토니 까르보나라', '진한 까르보나라', 15900, false, NOW(), NOW()),
       (16, '함박스테이크 정식', '수제 함박스테이크 정식', 14900, false, NOW(), NOW()),
       (16, '갈릭 치즈 브레드', '바삭한 갈릭 브레드', 6900, false, NOW(), NOW());

-- [홍콩반점 신사점 - store_id: 17]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (17, '짜장면', '부드러운 춘장 짜장면', 7000, false, NOW(), NOW()),
       (17, '짬뽕', '얼큰한 해물 짬뽕', 8000, false, NOW(), NOW()),
       (17, '탕수육 소', '바삭한 탕수육 소자', 13000, false, NOW(), NOW()),
       (17, '볶음밥', '고소한 새우 볶음밥', 8000, false, NOW(), NOW());

-- [백종원의 짜장면 - store_id: 18]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (18, '옛날짜장', '백종원 시그니처 옛날짜장', 7000, false, NOW(), NOW()),
       (18, '짬뽕', '칼칼한 백종원 짬뽕', 8000, false, NOW(), NOW()),
       (18, '간짜장', '진한 춘장 간짜장', 8000, false, NOW(), NOW()),
       (18, '군만두 6개', '바삭한 군만두', 5000, false, NOW(), NOW());

-- [가장맛있는족발 건대점 - store_id: 19]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (19, '족발 소', '쫄깃한 족발 소자', 25000, false, NOW(), NOW()),
       (19, '족발 중', '쫄깃한 족발 중자', 32000, false, NOW(), NOW()),
       (19, '앞발 소', '부드러운 앞발 소자', 27000, false, NOW(), NOW()),
       (19, '막국수', '시원한 메밀 막국수', 7000, false, NOW(), NOW());

-- [원할머니보쌈 종로점 - store_id: 20]
INSERT INTO menus (store_id, menu_name, menu_description, price, is_sold_out, created_at, modified_at)
VALUES (20, '보쌈 소', '원할머니 시그니처 보쌈 소', 30000, false, NOW(), NOW()),
       (20, '보쌈 중', '원할머니 시그니처 보쌈 중', 38000, false, NOW(), NOW()),
       (20, '순대 소', '쫄깃한 순대 소자', 12000, false, NOW(), NOW()),
       (20, '냉면', '시원한 물냉면', 8000, false, NOW(), NOW());

-- PK 시퀀스
ALTER TABLE users
    ALTER COLUMN user_id RESTART WITH 2;
ALTER TABLE owners
    ALTER COLUMN owner_id RESTART WITH 2;
ALTER TABLE stores
    ALTER COLUMN store_id RESTART WITH 21;
ALTER TABLE menus
    ALTER COLUMN menu_id RESTART WITH 81;
