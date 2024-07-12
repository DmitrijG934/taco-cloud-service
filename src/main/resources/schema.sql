CREATE TABLE IF NOT EXISTS taco_order
(
    id bigserial PRIMARY KEY,
    created_at timestamp NOT NULL,
    delivery jsonb NOT NULL,
    payment jsonb NOT NULL
);

CREATE TABLE IF NOT EXISTS taco
(
    id bigserial PRIMARY KEY,
    created_at timestamp NOT NULL,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS taco_order_ref
(
    taco_id bigint NOT NULL REFERENCES taco(id),
    order_id bigint NOT NULL REFERENCES taco_order(id)
);

CREATE TABLE IF NOT EXISTS ingredient
(
    id varchar(10) PRIMARY KEY,
    name varchar(255) NOT NULL,
    type varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS ingredient_taco_ref
(
    taco_id bigint NOT NULL REFERENCES taco(id),
    ingredient_id varchar(10) NOT NULL REFERENCES ingredient(id)
);