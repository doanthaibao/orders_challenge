CREATE TABLE discounts (
                           id binary(16) PRIMARY KEY,
                           amount INT NOT NULL,
                           percent DECIMAL(10, 2) NOT NULL
);
CREATE ALIAS UUIDStringToBytes FOR "com.interview.codepole.util.UUIDUtils.UUIDStringToBytes";

INSERT INTO discounts (id, amount, percent)
VALUES
    (UUIDStringToBytes('a137293e-5686-45a7-bd3b-8254a54feafc'), 5, 0.1),
    (UUIDStringToBytes('3d2a7904-3450-4758-8728-6d15c8fd865d'), 10, 0.15);


