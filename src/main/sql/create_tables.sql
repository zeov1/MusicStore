CREATE TABLE IF NOT EXISTS product_types
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description TEXT        NOT NULL DEFAULT 'No description.'
);

CREATE TABLE IF NOT EXISTS manufacturers
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(150)   NOT NULL,
    description     TEXT           NOT NULL DEFAULT 'No description.',
    price           DECIMAL(10, 2) NOT NULL,
    amount          INT            NOT NULL DEFAULT 0,
    units_sold      INT            NOT NULL DEFAULT 0,
    date_added      TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_archived     BOOLEAN        NOT NULL DEFAULT FALSE,
    product_type_id INT            NOT NULL,
    manufacturer_id INT            NOT NULL,
    FOREIGN KEY (product_type_id) REFERENCES product_types (id) ON DELETE CASCADE,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS photos
(
    id         SERIAL PRIMARY KEY,
    image_name VARCHAR(80) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product_photo_links
(
    id         SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    photo_id   INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (photo_id) REFERENCES photos (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(150) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    date_joined DATE         NOT NULL DEFAULT CURRENT_DATE,
    role        varchar      NOT NULL DEFAULT 'ROLE_USER'
);
