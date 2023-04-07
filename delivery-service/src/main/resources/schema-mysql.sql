USE `deliveries-db`;

create table if not exists deliveries (
     id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
     delivery_id VARCHAR(36),
      checkout_id VARCHAR(36),
    client_id VARCHAR(36),
    warehouse_location VARCHAR(50),
    phone_number VARCHAR(50),
    arrival_time DATE,
    shipping_update VARCHAR(50),
    street_address VARCHAR(50),
    city VARCHAR(50),
    country VARCHAR(50),
    province VARCHAR(50),
    postal_code VARCHAR(10),
    country_code VARCHAR(5)

    );

