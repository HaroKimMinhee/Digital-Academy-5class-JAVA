-- create database Onedaypichi

use Onedaypichi;

-- CREATE table Category (
-- 	id int auto_increment primary key,
--     name varchar(50) not null
-- );

-- create table Menu (
-- id int auto_increment primary key,
-- category_id int not null,
-- name varchar(100) not null,
-- price int not null,
-- stock int not null default 100,
-- foreign key (category_id) references Category(id)
-- );

-- INSERT INTO Category (name) VALUES ('피자'), ('치킨'), ('피자&치킨'), ('디저트'), ('음료');
-- select * from Category;

-- INSERT INTO Menu (category_id, name, price, stock) VALUES
-- (1, '콤비네이션 피자', 19000, 100),
-- (1, '포테이토 피자', 20000, 100),
-- (1, '베이컨 체다치즈 피자', 20000, 100),
-- (1, '불고기 피자', 18000, 100),
-- (1, '야채 피자', 18000, 100),
-- (1, '페페로니 피자', 19000, 100),
-- (1, '치즈 피자', 18000, 100),
-- (1, '옥수수 피자', 20000, 100),
-- (1, '하와이안 피자', 21000, 100),
-- (1, '고구마 피자', 20000, 100);

-- INSERT INTO Menu (category_id, name, price, stock) VALUES
-- (2, '양념 치킨', 18000, 100),
-- (2, '후라이드 치킨', 18000, 100),
-- (2, '소이갈릭 치킨', 20000, 100),
-- (2, '오리엔탈 치킨', 23000, 100),
-- (2, '양념반 후라이드 치킨', 19000, 100),
-- (2, '치즈스노윙 치킨', 20000, 100),
-- (2, '매콤치즈스노윙 치킨', 21000, 100),
-- (2, '찜닭', 21000, 100),
-- (2, '마늘 치킨', 20000, 100),
-- (2, '레드마블 치킨', 23000, 100);

-- INSERT INTO Menu (category_id, name, price, stock) VALUES
-- (3, '콤비네이션 피자&후라이드 세트', 20900, 100),
-- (3, '고구마 피자&후라이드 세트', 21000, 100),
-- (3, '페퍼간장치킨&콤비네이션 피자 세트', 19000, 100),
-- (3, '스테이크 피자&후라이드 세트', 20000, 100),
-- (3, '치즈 피자&후라이드 세트', 22000, 100),
-- (3, '스위트불고기 피자& 후라이드 세트', 21000, 100),
-- (3, '더블포테이토 피자&후라이드 세트', 21000, 100),
-- (3, '통새우불고기 피자&후라이드 세트', 24000, 100),
-- (3, '콤비네이션 피자& 치즈크리스피 치킨 세트', 23000, 100),
-- (3, '콤비네이션 피자&고추깐풍 치킨 세트', 22000, 100);

-- INSERT INTO Menu (category_id, name, price, stock) VALUES
-- (4, '클라우드 치즈케이크', 5000, 100),
-- (4, '초코 크레이프 케이크', 5000, 100),
-- (4, '블루베리 마카롱', 3000, 100),
-- (4, '바닐라 마카롱', 3000, 100),
-- (4, '다크초콜릿 마카롱', 3000, 100),
-- (4, '베이컨 치즈 토스트', 2500, 100),
-- (4, '까망베리 치즈 피낭시에', 3000, 100),
-- (4, '베이컨 체다&오믈렛 샌드위치', 4000, 100),
-- (4, '밀크 푸딩', 1000, 100),
-- (4, '치킨 베이컨 랩', 2500, 100);

-- INSERT INTO Menu (category_id, name, price, stock) VALUES
-- (5, '복숭아 아이스티', 3000, 100),
-- (5, '아이스 자몽 허니 블랙티', 3000, 100),
-- (5, '말차라떼', 4000, 100),
-- (5, '제주 까망 크림 프라푸치노', 4500, 100),
-- (5, '딸기글레이즈드 크림프라푸치노', 5000, 100),
-- (5, '아메리카노', 2000, 100),
-- (5, '화이트 초콜릿 모카', 3000, 100),
-- (5, '카라멜 마키아또', 3000, 100),
-- (5, '딸기 가득 요거트', 2000, 100),
-- (5, '레드 파워 패션 티', 3500, 100);

select * from Menu;