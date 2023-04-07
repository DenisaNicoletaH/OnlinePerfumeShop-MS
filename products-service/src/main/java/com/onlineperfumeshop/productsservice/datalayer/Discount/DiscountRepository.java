package com.onlineperfumeshop.productsservice.datalayer.Discount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    Discount findByDiscountIdentifier_DiscountId(String discountId);
}
