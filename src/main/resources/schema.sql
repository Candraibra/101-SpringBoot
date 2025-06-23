CREATE TABLE IF NOT EXISTS product
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    255
) NOT NULL,
    description VARCHAR
(
    255
),
    price DECIMAL
(
    10,
    2
) NOT NULL
    );

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    role     VARCHAR(50) DEFAULT 'USER'
);
