CREATE TABLE Truck (
                       chassisNumber VARCHAR(255) PRIMARY KEY,
                       licensePlate VARCHAR(255)
);

CREATE TABLE Item (
                      itemId SERIAL PRIMARY KEY,
                      itemName VARCHAR(255),
                      quantity INT,
                      unitPrice NUMERIC(10, 2)
);

CREATE TABLE "Order" (
                         orderId SERIAL PRIMARY KEY,
                         submittedDate DATE,
                         status VARCHAR(255),
                         deadlineDate DATE,
                         user_id BIGINT,
                         FOREIGN KEY (user_id) REFERENCES "User"(id)
);

CREATE TABLE Delivery (
                          deliveryId SERIAL PRIMARY KEY,
                          deliveryDate DATE,
                          order_id BIGINT REFERENCES "Order"(orderId),
                          truck_chassis_number VARCHAR(255) REFERENCES Truck(chassisNumber)
);

CREATE TABLE OrderItem (
                           id SERIAL PRIMARY KEY,
                           order_id BIGINT REFERENCES "Order"(orderId),
                           item_id BIGINT REFERENCES Item(itemId),
                           quantity INT
);

CREATE TABLE Token (
                       id SERIAL PRIMARY KEY,
                       token VARCHAR(255),
                       tokenType VARCHAR(255),
                       expired BOOLEAN,
                       revoked BOOLEAN,
                       user_id BIGINT REFERENCE
