USE `products-db`;

create table if not exists products (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    inventory_id VARCHAR(50),
    discount_id VARCHAR(50),
    product_id VARCHAR(36),
    name VARCHAR(50),
    brand VARCHAR (50),
    price DOUBLE,
    scent_type VARCHAR(50),
    date_produced VARCHAR(50),
    status VARCHAR(50)

    );

create table if not exists inventories (
     id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    inventory_id VARCHAR(36),
    last_inventory_update DATE

    );

create table if not exists discounts (
id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    discount_id VARCHAR(36),
    sale_percent INTEGER,
    new_prices DOUBLE,
    sale_status VARCHAR(50)

    );




