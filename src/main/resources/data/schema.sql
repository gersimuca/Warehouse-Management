CREATE TABLE IF NOT EXISTS delivery (
                                        delivery_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        delivery_date DATE,
                                        order_id BIGINT,
                                        truck_chassis_number VARCHAR(255),
    FOREIGN KEY (order_id) REFERENCES order (order_id),
    FOREIGN KEY (truck_chassis_number) REFERENCES truck (chassis_number)
    );

CREATE TABLE IF NOT EXISTS item (
                                    item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    item_name VARCHAR(255),
    quantity INT,
    unit_price DOUBLE
    );

CREATE TABLE IF NOT EXISTS `order` (
                                       order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       submitted_date DATE,
                                       status VARCHAR(255),
    deadline_date DATE,
    decline_reason VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user (id)
    );

CREATE TABLE IF NOT EXISTS order_item (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          order_id BIGINT,
                                          item_id BIGINT,
                                          quantity INT,
                                          FOREIGN KEY (order_id) REFERENCES `order` (order_id),
    FOREIGN KEY (item_id) REFERENCES item (item_id)
    );

CREATE TABLE IF NOT EXISTS token (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     token VARCHAR(255),
    token_type VARCHAR(255),
    expired BOOLEAN,
    revoked BOOLEAN,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user (id)
    );

CREATE TABLE IF NOT EXISTS truck (
                                     chassis_number VARCHAR(255) PRIMARY KEY,
    license_plate VARCHAR(255) UNIQUE,
    capacity INT
    );

CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
    );
