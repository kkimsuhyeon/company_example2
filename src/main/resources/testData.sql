INSERT INTO customer (name)
VALUES ('customer1'),('customer2'),('customer3'),('customer4');

INSERT INTO beverage_category (name)
VALUES ('category1'), ('category2'), ('category3'), ('category4'), ('category5'), ('category6');

INSERT INTO beverage (name, price, beverage_category_id)
VALUES ('beverage1', 1000, null),('beverage2', 1500, null), ('beverage3', 2000, 1),('beverage4', 2500, 1);

INSERT INTO receipt (receipt_status, customer_id,created_at, modified_at)
VALUES ('wait', 1, now(), now()),
('wait', 1, now(), now()),
('wait', 2, now(), now()),
('wait', 3, now(), now()),
('wait', 4, now(), now());

INSERT INTO purchase (purchase_status, price, receipt_id, beverage_id, created_at, modified_at)
VALUES ('success', 1000,1, 1, now(), now()),
('success', 1000,2, 1, now(), now()),
('wait', 1000,3, 1, now(), now()),
('wait', 1000,4, 1, now(), now()),
('wait',1000, 1, 2, now(), now()),
('wait', 1000,2, 2, now(), now()),
('wait', 1000,1, 3, now(), now()),
('wait', 1000,1, 4, now(), now()),
('wait', 1000,1, 4, now(), now()),
('wait', 1000,4, 4, now(), now()),
('wait', 1000,4, 3, now(), now());

INSERT INTO payment (payment_status, price, method, purchase_id, created_at, modified_at)
VALUES ('success', 3000, 'CARD', 1 , now(), now()),
('success',3000,'CARD', 2, now(), now()),
('fail',3000,'CARD', 3, now(), now()),
('fail',3000,'CARD', 3, now(), now());


