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
INSERT INTO users (user_id, username, phone_number, email, password, role, created_at, modified_at)
VALUES (1, '김사장', '010-1111-1111', 'owner1@test.com', 'pass123', 'OWNER', NOW(), NOW());

-- 3. 사장님 데이터 (modified_at 추가)
INSERT INTO owners (user_id, owner_name, business_number, created_at, modified_at)
VALUES (1, '김사장', '123-45-67890', NOW(), NOW());

-- 4. 식당 데이터 (modified_at 추가)
-- [치킨]
INSERT INTO stores (store_id, owner_id, category_id, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (1, 1, 6, 'BBQ 강남점', '123-45-67890', '서울시 강남구', '1층', 'OPEN', 15000, 3000, NOW(), NOW()),
       (2, 1, 6, '교촌치킨 압구정점', '123-45-67891', '서울시 강남구', '102호', 'OPEN', 16000, 3000, NOW(), NOW()),
       (3, 1, 6, 'BHC 청담점', '123-45-67892', '서울시 강남구', '2층', 'OPEN', 15000, 2000, NOW(), NOW());

-- [일식]
INSERT INTO stores (store_id, owner_id, category_id, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (4, 1, 5, '상무초밥 송파점', '123-45-67893', '서울시 송파구', '101호', 'OPEN', 20000, 4000, NOW(), NOW()),
       (5, 1, 5, '홍대돈부리 신촌점', '123-45-67894', '서울시 서대문구', '1층', 'OPEN', 12000, 2500, NOW(), NOW());

-- [한식]
INSERT INTO stores (store_id, owner_id, category_id, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (6, 1, 2, '김밥천국 강남본점', '123-45-67895', '서울시 강남구', '1층', 'OPEN', 10000, 2000, NOW(), NOW()),
       (7, 1, 2, '큰맘할매순대국 역삼점', '123-45-67896', '서울시 강남구', '지하 1층', 'OPEN', 14000, 3000, NOW(), NOW()),
       (8, 1, 2, '본죽 서초점', '123-45-67897', '서울시 서초구', '1층', 'OPEN', 12000, 3500, NOW(), NOW());

-- [분식]
INSERT INTO stores (store_id, owner_id, category_id, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (9, 1, 3, '동대문엽기떡볶이 논현점', '123-45-67898', '서울시 강남구', '2층', 'OPEN', 14000, 3000, NOW(), NOW()),
       (10, 1, 3, '신전떡볶이 삼성점', '123-45-67899', '서울시 강남구', '1층', 'OPEN', 13000, 2500, NOW(), NOW());

-- [카페·디저트]
INSERT INTO stores (store_id, owner_id, category_id, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (11, 1, 4, '메가MGC커피 선릉점', '123-45-67900', '서울시 강남구', '1층', 'OPEN', 8000, 2000, NOW(), NOW()),
       (12, 1, 4, '스타벅스 강남역점', '123-45-67901', '서울시 강남구', '1, 2층', 'OPEN', 15000, 3000, NOW(), NOW());

-- [피자]
INSERT INTO stores (store_id, owner_id, category_id, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (13, 1, 7, '도미노피자 반포점', '123-45-67902', '서울시 서초구', '1층', 'OPEN', 20000, 0, NOW(), NOW()),
       (14, 1, 7, '파파존스 서초점', '123-45-67903', '서울시 서초구', '1층', 'OPEN', 18000, 2000, NOW(), NOW());

-- [양식]
INSERT INTO stores (store_id, owner_id, category_id, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (15, 1, 8, '아웃백스테이크하우스 강남점', '123-45-67904', '서울시 강남구', '3층', 'OPEN', 30000, 5000, NOW(), NOW()),
       (16, 1, 8, '서가앤쿡 홍대점', '123-45-67905', '서울시 마포구', '2층', 'OPEN', 20000, 4000, NOW(), NOW());

-- [중식]
INSERT INTO stores (store_id, owner_id, category_id, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (17, 1, 9, '홍콩반점0410 신사점', '123-45-67906', '서울시 강남구', '지하 1층', 'OPEN', 13000, 2500, NOW(), NOW()),
       (18, 1, 9, '백종원의 짜장면', '123-45-67907', '서울시 종로구', '1층', 'OPEN', 12000, 2000, NOW(), NOW());

-- [족발·보쌈]
INSERT INTO stores (store_id, owner_id, category_id, store_name, business_number, store_address, store_address_details,
                    status, min_order_amount, base_delivery_fee, created_at, modified_at)
VALUES (19, 1, 10, '가장맛있는족발 건대점', '123-45-67908', '서울시 광진구', '1층', 'OPEN', 25000, 3000, NOW(), NOW()),
       (20, 1, 10, '원할머니보쌈 종로점', '123-45-67909', '서울시 종로구', '2층', 'OPEN', 30000, 3000, NOW(), NOW());


-- 5. 메뉴 데이터 (modified_at 추가)
-- [store_id: 1, BBQ]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (1, 1, '황금올리브 치킨', '바삭함의 끝판왕', 23000, NOW(), NOW()),
       (2, 1, '황금올리브 양념치킨', '달콤매콤한 양념치킨', 24500, NOW(), NOW()),
       (3, 1, '자메이카 통다리구이', '저크소스를 바른 통다리', 24000, NOW(), NOW());

-- [store_id: 2, 교촌치킨]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (4, 2, '허니콤보', '달콤한 꿀과 간장의 조화', 23000, NOW(), NOW()),
       (5, 2, '레드콤보', '맛있게 매운맛', 23000, NOW(), NOW()),
       (6, 2, '교촌오리지날', '마늘 간장 소스의 정석', 19000, NOW(), NOW());

-- [store_id: 3, BHC]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (7, 3, '뿌링클', '마법의 시즈닝 팍팍', 21000, NOW(), NOW()),
       (8, 3, '맛초킹', '매콤짭짤한 숙성 간장 치킨', 21000, NOW(), NOW()),
       (9, 3, '달콤바삭 치즈볼', '모짜렐라 치즈가 듬뿍', 5500, NOW(), NOW());

-- [store_id: 4, 상무초밥]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (10, 4, '모듬초밥 10p', '신선한 활어 초밥', 22000, NOW(), NOW()),
       (11, 4, '특선초밥 10p', '더 고급스러운 재료로 만든 특선', 27000, NOW(), NOW()),
       (12, 4, '연어초밥 8p', '입안에서 녹는 생연어', 18000, NOW(), NOW());

-- [store_id: 5, 홍대돈부리]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (13, 5, '가츠동', '바삭한 돈가스가 올라간 덮밥', 10500, NOW(), NOW()),
       (14, 5, '사케동', '신선한 생연어 덮밥', 15000, NOW(), NOW()),
       (15, 5, '에비동', '통통한 새우튀김 덮밥', 11500, NOW(), NOW());

-- [store_id: 6, 김밥천국]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (16, 6, '원조김밥', '추억의 맛', 3500, NOW(), NOW()),
       (17, 6, '제육덮밥', '매콤달콤 제육볶음', 8000, NOW(), NOW()),
       (18, 6, '스페셜 정식', '돈까스+김밥+쫄면', 12000, NOW(), NOW());

-- [store_id: 7, 큰맘할매순대국]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (19, 7, '순대국', '진하고 뽀얀 국물', 9000, NOW(), NOW()),
       (20, 7, '뼈해장국', '얼큰한 국물과 부드러운 고기', 10000, NOW(), NOW()),
       (21, 7, '맛보기 수육', '부드럽게 삶아낸 돼지고기', 12000, NOW(), NOW());

-- [store_id: 8, 본죽]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (22, 8, '전복죽', '기력 회복에 좋은 영양죽', 13000, NOW(), NOW()),
       (23, 8, '쇠고기야채죽', '담백하고 든든한 한 끼', 10500, NOW(), NOW()),
       (24, 8, '낙지김치죽', '매콤 칼칼한 해장죽', 11000, NOW(), NOW());

-- [store_id: 9, 동대문엽기떡볶이]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (25, 9, '엽기떡볶이', '스트레스 풀리는 매운맛', 14000, NOW(), NOW()),
       (26, 9, '모듬튀김', '김말이, 야채, 만두 등', 2000, NOW(), NOW()),
       (27, 9, '참치마요 주먹밥', '매운맛 중화 꿀조합', 3500, NOW(), NOW());

-- [store_id: 10, 신전떡볶이]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (28, 10, '치즈떡볶이', '모짜렐라 치즈 듬뿍', 5500, NOW(), NOW()),
       (29, 10, '오뎅튀김(5개)', '신전의 시그니처 튀김', 1700, NOW(), NOW()),
       (30, 10, '신전치즈김밥', '매콤한 소스와 치즈의 만남', 4000, NOW(), NOW());

-- [store_id: 11, 메가MGC커피]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (31, 11, '아이스 아메리카노', '대용량 사이즈 갓성비', 2000, NOW(), NOW()),
       (32, 11, '큐브라떼', '에스프레소 얼음이 들어간 라떼', 4200, NOW(), NOW()),
       (33, 11, '퐁크러쉬', '죠리퐁이 듬뿍 올라간 쉐이크', 3900, NOW(), NOW());

-- [store_id: 12, 스타벅스]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (34, 12, '카페 아메리카노', '에스프레소의 강렬한 풍미', 4500, NOW(), NOW()),
       (35, 12, '자몽 허니 블랙티', '달콤 쌉쌀한 인기 메뉴', 5700, NOW(), NOW()),
       (36, 12, '부드러운 생크림 카스텔라', '우유 생크림이 가득한 디저트', 4500, NOW(), NOW());

-- [store_id: 13, 도미노피자]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (37, 13, '포테이토 피자(L)', '남녀노소 좋아하는 베스트셀러', 27900, NOW(), NOW()),
       (38, 13, '블랙타이거 슈림프(L)', '통통한 새우와 와규 크럼블', 36900, NOW(), NOW()),
       (39, 13, '페퍼로니(L)', '정통 아메리칸 스타일', 25900, NOW(), NOW());

-- [store_id: 14, 파파존스]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (40, 14, '수퍼 파파스(L)', '고기와 야채가 듬뿍 들어간 시그니처', 28500, NOW(), NOW()),
       (41, 14, '존스 페이버릿(L)', '6종류의 치즈와 페퍼로니', 29500, NOW(), NOW()),
       (42, 14, '치즈 롤', '도우 끝에 스트링 치즈가 가득', 4000, NOW(), NOW());

-- [store_id: 15, 아웃백스테이크하우스]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (43, 15, '투움바 파스타', '깊고 진한 크림소스 파스타', 25900, NOW(), NOW()),
       (44, 15, '갈릭 립아이', '구운 마늘이 올라간 꽃등심 스테이크', 47900, NOW(), NOW()),
       (45, 15, '베이비 백 립', '특제 소스를 발라 구운 바비큐', 40900, NOW(), NOW());

-- [store_id: 16, 서가앤쿡]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (46, 16, '목살 스테이크 샐러드', '부드러운 목살과 신선한 샐러드', 23800, NOW(), NOW()),
       (47, 16, '베이컨 까르보나라', '베이컨이 듬뿍 들어간 크림 스파게티', 11800, NOW(), NOW()),
       (48, 16, '새우 필라프', '통통한 새우를 볶아낸 밥', 11800, NOW(), NOW());

-- [store_id: 17, 홍콩반점0410]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (49, 17, '짬뽕', '불맛 나는 얼큰한 국물', 7000, NOW(), NOW()),
       (50, 17, '짜장면', '달콤 짭짤한 기본 짜장', 6000, NOW(), NOW()),
       (51, 17, '탕수육(소)', '겉바속촉 쫄깃한 탕수육', 14900, NOW(), NOW());

-- [store_id: 18, 백종원의 짜장면]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (52, 18, '고추짜장', '청양고추로 맛을 낸 매콤한 짜장', 7500, NOW(), NOW()),
       (53, 18, '해물짬뽕', '해산물이 가득한 시원한 짬뽕', 8500, NOW(), NOW()),
       (54, 18, '군만두(8개)', '바삭하게 튀겨낸 만두', 5000, NOW(), NOW());

-- [store_id: 19, 가장맛있는족발]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (55, 19, '반반족발', '일반 족발과 불족발을 한 번에', 39000, NOW(), NOW()),
       (56, 19, '직화불족발', '매콤한 소스를 발라 직화로 구운 족발', 36000, NOW(), NOW()),
       (57, 19, '큰쟁반국수', '새콤달콤 족발 필수 단짝', 12000, NOW(), NOW());

-- [store_id: 20, 원할머니보쌈]
INSERT INTO menus (menu_id, store_store_id, menu_name, menu_description, price, created_at, modified_at)
VALUES (58, 20, '모둠보쌈(소)', '다양한 김치와 함께 즐기는 보쌈', 34000, NOW(), NOW()),
       (59, 20, '매운火보쌈', '화끈하게 매운 보쌈', 35000, NOW(), NOW()),
       (60, 20, '새싹쟁반무침면', '신선한 새싹채소가 들어간 비빔면', 11000, NOW(), NOW());

-- PK 시퀀스
ALTER TABLE users ALTER COLUMN user_id RESTART WITH 3;
ALTER TABLE stores ALTER COLUMN store_id RESTART WITH 11;
ALTER TABLE menus ALTER COLUMN menu_id RESTART WITH 7;
ALTER TABLE reviews ALTER COLUMN review_id RESTART WITH 3;
