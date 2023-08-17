INSERT INTO cars (price, year, engine_power)
VALUES (25000, 2022, 200),
       (18000, 2021, 180),
       (30000, 2023, 250);

INSERT INTO sellers (name)
VALUES ('AutoWorld Dealers'),
       ('City Cars'),
       ('CarZone Express');

INSERT INTO availability (seller_id, car_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 3);

INSERT INTO owners (name, car_id)
VALUES ('John Smith', 1),
       ('Emily Johnson', 2),
       ('Michael Brown', 3),
       ('Sophia Williams', 2),
       ('David Taylor', 1),
       ('Joe Biden', null);