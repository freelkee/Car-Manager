INSERT INTO cars (price, year, engine_power)
VALUES (25000, 2022, 200),
       (18000, 2021, 180),
       (30000, 2023, 250),
       (22000, 2022, 190),
       (21000, 2020, 210);

INSERT INTO sellers (name)
VALUES ('AutoWorld Dealers'),
       ('City Cars'),
       ('CarZone Express'),
       ('TopCar Sales'),
       ('Elite Motors');

INSERT INTO sellers_to_cars (seller_id, car_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 3),
       (4, 4),
       (4, 5),
       (5, 1),
       (5, 2);

INSERT INTO owners (name, car_id, budget)
VALUES ('John Smith', 1, 20000),
       ('Emily Johnson', 1, 8000),
       ('Michael Brown', 3, 40000),
       ('Sophia Williams', 2, 20000),
       ('David Taylor', null, 18000),
       ('Joe Biden', 2, 21000),
       ('Jennifer White', 5, 5000),
       ('Robert Davis', 5, 60000),
       ('Maria Martinez', 3, 15000),
       ('Daniel Wilson', null, 30000);