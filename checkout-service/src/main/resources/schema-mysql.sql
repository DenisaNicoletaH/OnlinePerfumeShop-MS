USE `checkout-db`;
create table if not exists checkout (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
     checkout_id VARCHAR(36),
    product_id VARCHAR(36),
    amount DOUBLE,
    taxes DOUBLE,
    shipping DOUBLE,
    end_of_sale DATE,
    payment_type VARCHAR(50),
    total_amount DOUBLE


    );

