CREATE TABLE users (
                       id binary(16) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL
);

CREATE TABLE orders (
                        id binary(16) PRIMARY KEY,
                        user_id binary(16) NOT NULL,
                        total DECIMAL(10, 2) NOT NULL,
                        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_details (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,
                               amount INT NOT NULL,
                               product_id binary(16) NOT NULL,
                               price_at_purchase DECIMAL(10, 2) NOT NULL,
                               order_id binary(16) NOT NULL,
                               FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE ALIAS if not exists UUIDStringToBytes FOR "com.interview.codepole.util.UUIDUtils.UUIDStringToBytes";

INSERT INTO users (id, name, email)
VALUES (UUIDStringToBytes('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1'), 'User 1', 'user1@example.com'),
       (UUIDStringToBytes('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e2'), 'User 2', 'user2@example.com'),
       (UUIDStringToBytes('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e3'), 'User 3', 'user3@example.com');


INSERT INTO orders (id, user_id, total,date)
VALUES (UUIDStringToBytes('62f0b710-feca-4cb7-8539-f7bd6c6a8a3a'),
        UUIDStringToBytes('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1'),  50.00, '2024-04-01 00:00:00');
INSERT INTO order_details (name, product_id, amount, price_at_purchase, order_id)
VALUES ('Product 1', UUIDStringToBytes('28962655-a812-40fc-b223-9e3f7e04c14d'), 1, 10.00, UUIDStringToBytes('62f0b710-feca-4cb7-8539-f7bd6c6a8a3a')),
       ('Product 2', UUIDStringToBytes('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1'), 2, 20.00, UUIDStringToBytes('62f0b710-feca-4cb7-8539-f7bd6c6a8a3a'));



INSERT INTO orders (id, user_id, total, date)
VALUES (UUIDStringToBytes('f7cb546d-357b-4f38-b7f7-bb32fbb73168')
       ,UUIDStringToBytes('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e2'), 50.00, '2024-04-04 00:00:00');
INSERT INTO order_details (name, product_id, amount, price_at_purchase, order_id)
VALUES ('Product 1', UUIDStringToBytes('28962655-a812-40fc-b223-9e3f7e04c14d'), 1, 10.00, UUIDStringToBytes('f7cb546d-357b-4f38-b7f7-bb32fbb73168')),
       ('Product 2', UUIDStringToBytes('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1'), 2, 20.00, UUIDStringToBytes('f7cb546d-357b-4f38-b7f7-bb32fbb73168'));

INSERT INTO orders (id, user_id, total, date)
VALUES (UUIDStringToBytes('c78f6062-ddd5-4c2a-9660-0b5259e6a2b4'),
        UUIDStringToBytes('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e2'), 50.00, '2024-04-05 00:00:00');
INSERT INTO order_details (name, product_id, amount, price_at_purchase, order_id)
VALUES ('Product 1', UUIDStringToBytes('28962655-a812-40fc-b223-9e3f7e04c14d'), 1, 10.00, UUIDStringToBytes('c78f6062-ddd5-4c2a-9660-0b5259e6a2b4')),
       ('Product 2', UUIDStringToBytes('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1'), 2, 20.00, UUIDStringToBytes('c78f6062-ddd5-4c2a-9660-0b5259e6a2b4'));
