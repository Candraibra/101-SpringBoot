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