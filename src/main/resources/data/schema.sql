CREATE TABLE "User" (
                        id SERIAL PRIMARY KEY,
                        username VARCHAR(255) UNIQUE,
                        password VARCHAR(255),
                        role VARCHAR(50)
);

CREATE TABLE "Order" (
                         orderId SERIAL PRIMARY KEY,
                         submittedDate DATE,
                         status VARCHAR(50),
                         deadlineDate DATE,
                         userId INT REFERENCES "User"(id)
);

CREATE TABLE Item (
                      itemId SERIAL PRIMARY KEY,
                      itemName VARCHAR(255),
                      quantity INT,
                      unitPrice DOUBLE PRECISION,
                      orderId INT REFERENCES "Order"(orderId)
);

CREATE TABLE Truck (
                       chassisNumber VARCHAR(255) PRIMARY KEY,
                       licensePlate VARCHAR(255) UNIQUE
);

CREATE TABLE Delivery (
                          deliveryId SERIAL PRIMARY KEY,
                          deliveryDate DATE,
                          orderId INT REFERENCES "Order"(orderId),
                          truckChassisNumber VARCHAR(255) REFERENCES Truck(chassisNumber)
);

CREATE TABLE PasswordResetRequest (
                                      requestId SERIAL PRIMARY KEY,
                                      userId INT REFERENCES "User"(id),
                                      requestDate TIMESTAMP
);
