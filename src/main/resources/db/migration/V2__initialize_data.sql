INSERT INTO discounts (id, amount, percent)
VALUES (UUID_TO_BIN('a137293e-5686-45a7-bd3b-8254a54feafc'), 5, 0.1),
       (UUID_TO_BIN('3d2a7904-3450-4758-8728-6d15c8fd865d'), 10, 0.15);

INSERT INTO users (id, name, email)
VALUES (UUID_TO_BIN('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1'), 'User 1', 'user1@example.com'),
       (UUID_TO_BIN('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e2'), 'User 2', 'user2@example.com'),
       (UUID_TO_BIN('3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e3'), 'User 3', 'user3@example.com');



