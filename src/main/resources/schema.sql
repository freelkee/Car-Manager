CREATE TABLE IF NOT EXISTS cars
(
    id           serial PRIMARY KEY,
    price        integer,
    year         integer,
    engine_power integer
);
CREATE TABLE IF NOT EXISTS car_sellers
(
    id   serial PRIMARY KEY,
    name varchar(100)
);
CREATE TABLE IF NOT EXISTS availability
(
    id           serial PRIMARY KEY,
    car_seller_id integer,
    car_id        integer,
    FOREIGN KEY (car_seller_id) REFERENCES car_sellers (id),
    FOREIGN KEY (car_id) REFERENCES cars (id)
);
CREATE TABLE IF NOT EXISTS owners
(
    id     serial PRIMARY KEY,
    name   varchar(100),
    car_id integer,
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

