INSERT INTO beverage_category (name)
VALUES ('category1'), ('category2'), ('category3'), ('category4'), ('category5'), ('category6');

INSERT INTO customer (name)
VALUES ('customer1'),('customer2'),('customer3'),('customer4');

INSERT INTO beverage (name, beverage_category_id)
VALUES ('beverage1', null),('beverage2', null), ('beverage3', 1),('beverage4', 1);


INSERT INTO orders (status, customer_id, beverage_id, created_at, modified_at)
VALUES ('success', 1, 1, now(), now()),
('success', 2, 1, now(), now()),
('wait', 3, 1, now(), now()),
('wait', 4, 1, now(), now()),
('wait', 1, 2, now(), now()),
('wait', 1, 3, now(), now()),
('wait', 2, 2, now(), now()),
('wait', 4, 3, now(), now());

INSERT INTO payment (status, order_id, created_at, modified_at)
VALUES ('success', 1 , now(), now()),
('success', 2, now(), now()),
('fail', 3, now(), now()),
('fail', 3, now(), now());


