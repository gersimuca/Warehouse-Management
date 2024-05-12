-- Delivery table records
INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-01', 1, 'TRK12345' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-01');

INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-02', 2, 'TRK54321' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-02');

INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-03', 3, 'TRK98765' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-03');

INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-04', 4, 'TRK24680' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-04');

INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-05', 5, 'TRK13579' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-05');

INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-06', 6, 'TRK55555' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-06');

INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-07', 7, 'TRK77777' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-07');

INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-08', 8, 'TRK88888' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-08');

INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-09', 9, 'TRK99999' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-09');

INSERT INTO delivery (delivery_date, order_id, truck_chassis_number)
SELECT '2024-05-10', 10, 'TRK11111' FROM dual WHERE NOT EXISTS (SELECT * FROM delivery WHERE delivery_date = '2024-05-10');

-- Item table records
INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Widget', 100, 10.50 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Widget');

INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Gadget', 50, 25.75 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Gadget');

INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Tool', 200, 8.99 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Tool');

INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Accessory', 150, 5.25 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Accessory');

INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Component', 300, 3.99 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Component');

INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Device', 75, 50.00 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Device');

INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Equipment', 120, 15.49 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Equipment');

INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Material', 250, 12.75 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Material');

INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Product', 180, 20.00 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Product');

INSERT INTO item (item_name, quantity, unit_price)
SELECT 'Supply', 90, 7.25 FROM dual WHERE NOT EXISTS (SELECT * FROM item WHERE item_name = 'Supply');

-- Order table records
INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-15', 'OPEN', '2024-05-15', NULL, 1 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-15');

INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-16', 'CLOSED', '2024-05-16', NULL, 2 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-16');

INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-17', 'OPEN', '2024-05-17', NULL, 3 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-17');

INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-18', 'CLOSED', '2024-05-18', NULL, 4 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-18');

INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-19', 'OPEN', '2024-05-19', NULL, 5 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-19');

INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-20', 'CLOSED', '2024-05-20', NULL, 6 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-20');

INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-21', 'OPEN', '2024-05-21', NULL, 7 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-21');

INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-22', 'CLOSED', '2024-05-22', NULL, 8 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-22');

INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-23', 'OPEN', '2024-05-23', NULL, 9 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-23');

INSERT INTO `order` (submitted_date, status, deadline_date, decline_reason, user_id)
SELECT '2024-04-24', 'CLOSED', '2024-05-24', NULL, 10 FROM dual WHERE NOT EXISTS (SELECT * FROM `order` WHERE submitted_date = '2024-04-24');

-- Order Item table records
INSERT INTO order_item (order_id, item_id, quantity)
SELECT 1, 1, 10 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 1 AND item_id = 1);

INSERT INTO order_item (order_id, item_id, quantity)
SELECT 1, 2, 5 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 1 AND item_id = 2);

INSERT INTO order_item (order_id, item_id, quantity)
SELECT 2, 3, 20 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 2 AND item_id = 3);

INSERT INTO order_item (order_id, item_id, quantity)
SELECT 2, 4, 15 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 2 AND item_id = 4);

INSERT INTO order_item (order_id, item_id, quantity)
SELECT 3, 5, 30 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 3 AND item_id = 5);

INSERT INTO order_item (order_id, item_id, quantity)
SELECT 3, 6, 25 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 3 AND item_id = 6);

INSERT INTO order_item (order_id, item_id, quantity)
SELECT 4, 7, 40 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 4 AND item_id = 7);

INSERT INTO order_item (order_id, item_id, quantity)
SELECT 4, 8, 35 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 4 AND item_id = 8);

INSERT INTO order_item (order_id, item_id, quantity)
SELECT 5, 9, 50 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 5 AND item_id = 9);

INSERT INTO order_item (order_id, item_id, quantity)
SELECT 5, 10, 45 FROM dual WHERE NOT EXISTS (SELECT * FROM order_item WHERE order_id = 5 AND item_id = 10);

-- Token table records
INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'token123', 'ACCESS', FALSE, FALSE, 1 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'token123');

INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'token456', 'REFRESH', FALSE, FALSE, 2 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'token456');

INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'token789', 'ACCESS', FALSE, FALSE, 3 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'token789');

INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'tokenABC', 'REFRESH', FALSE, FALSE, 4 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'tokenABC');

INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'tokenDEF', 'ACCESS', FALSE, FALSE, 5 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'tokenDEF');

INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'tokenGHI', 'REFRESH', FALSE, FALSE, 6 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'tokenGHI');

INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'tokenJKL', 'ACCESS', FALSE, FALSE, 7 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'tokenJKL');

INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'tokenMNO', 'REFRESH', FALSE, FALSE, 8 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'tokenMNO');

INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'tokenPQR', 'ACCESS', FALSE, FALSE, 9 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'tokenPQR');

INSERT INTO token (token, token_type, expired, revoked, user_id)
SELECT 'tokenSTU', 'REFRESH', FALSE, FALSE, 10 FROM dual WHERE NOT EXISTS (SELECT * FROM token WHERE token = 'tokenSTU');

-- Truck table records
INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK12345', 'ABC123', 5000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK12345');

INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK54321', 'DEF456', 6000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK54321');

INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK98765', 'GHI789', 7000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK98765');

INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK24680', 'JKL012', 8000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK24680');

INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK13579', 'MNO345', 9000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK13579');

INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK55555', 'PQR678', 10000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK55555');

INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK77777', 'STU901', 11000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK77777');

INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK88888', 'VWX234', 12000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK88888');

INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK99999', 'YZA567', 13000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK99999');

INSERT INTO truck (chassis_number, license_plate, capacity)
SELECT 'TRK11111', 'BCD890', 14000 FROM dual WHERE NOT EXISTS (SELECT * FROM truck WHERE chassis_number = 'TRK11111');

-- User table records
INSERT INTO user (username, password, role)
VALUES ('user1', '$2a$10$0YqFNkV9fv4p4gCnHC9NZuPTIwFpeOQsJv8E7yPWWwe7uUzLSzqzK', 'SYSTEM_ADMIN'),
       ('user2', '$2a$10$uQvKxFgI9iHDTNdtTl.w6eGhzg96IebA7vRPIB6LpsBTr6Vo0AtQy', 'WAREHOUSE_MANAGER'),
       ('user3', '$2a$10$Um7b44oDz4eWmmB3hJy4I.viJ1zeL/wIMnIyDlqU6nBNt0ymNN7gm', 'CLIENT'),
       ('user4', '$2a$10$ZkLj20kf8GJfMmamzZzBQuB1lp6Vv79iU9BuCmb7qyMm4grHf5sRm', 'SYSTEM_ADMIN'),
       ('user5', '$2a$10$q2xm90m9KnGKrUn.Lke3Nu7VmFyHC6kDoDP3OkDsJXgC.bQlBQ7va', 'WAREHOUSE_MANAGER'),
       ('user6', '$2a$10$Y0I3MMKwPN3RzUkf1FRAq.RWz8sg0tmD9J1yEx/IbCPgH4FVMUu72', 'CLIENT'),
       ('user7', '$2a$10$H1Q5enp90vTn.iD.Fd4J8u4NNvMYDQUMVgGRcZTbmxXnPHOmt0De2', 'SYSTEM_ADMIN'),
       ('user8', '$2a$10$g8DIo3d86Xhtj0.R0w8kluYYTFKUzvqRv8UbXVgWEz7h0tNoCWWb6', 'WAREHOUSE_MANAGER'),
       ('user9', '$2a$10$yM9hYHZF81rfgmZaxapO4eJ5bmc9MDO.SIQVo5Mj3sjFhrmKgJC3y', 'CLIENT'),
       ('user10', '$2a$10$WuHsV5B7WwrKPq41bq.FgOKYh82/CH7jt8HTjC9z2D4cC8qHWh6JG', 'SYSTEM_ADMIN');


