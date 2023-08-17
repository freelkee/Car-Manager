DROP TABLE IF EXISTS availability;
DROP TABLE IF EXISTS owners;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS sellers;
CREATE TABLE IF NOT EXISTS cars
(
    id           serial PRIMARY KEY,
    price        integer,
    year         integer,
    engine_power integer
);

CREATE TABLE IF NOT EXISTS sellers
(
    id   serial PRIMARY KEY,
    name varchar(100)
);
CREATE TABLE IF NOT EXISTS availability
(
    id           serial PRIMARY KEY,
    seller_id integer,
    car_id        integer,
    FOREIGN KEY (seller_id) REFERENCES sellers (id),
    FOREIGN KEY (car_id) REFERENCES cars (id)
);
CREATE TABLE IF NOT EXISTS owners
(
    id     serial PRIMARY KEY,
    name   varchar(100),
    car_id integer,
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

