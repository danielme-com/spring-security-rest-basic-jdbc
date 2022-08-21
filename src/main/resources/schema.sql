CREATE TABLE users
(
    id       INTEGER      NOT NULL,
    enabled  BOOLEAN      NOT NULL,
    name     VARCHAR(20)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol      VARCHAR(10)  NOT NULL,
    PRIMARY KEY (id)
);