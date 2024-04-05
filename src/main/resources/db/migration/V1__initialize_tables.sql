CREATE TABLE users (
                        id binary(16) PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL
);

CREATE TABLE discounts (
                           id binary(16) PRIMARY KEY,
                           amount INT NOT NULL,
                           percent DECIMAL(10, 2) NOT NULL
);

CREATE TABLE orders (
                        id binary(16) PRIMARY KEY,
                        user_id binary(16) NOT NULL,
                        discount DECIMAL(10, 2) DEFAULT 0,
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

