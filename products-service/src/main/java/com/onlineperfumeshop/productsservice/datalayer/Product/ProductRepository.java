package com.onlineperfumeshop.productsservice.datalayer.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


    Product findByProductIdentifier_ProductId(String productId);

    List<Product> getAllByInventoryIdentifier_InventoryIdAndDiscountIdentifier_DiscountId(String inventoryId, String discountId);
    List<Product> getProductsByInventoryIdentifier_InventoryId(String inventoryId);

    List<Product> findAllProductsByBrand(String brand);

    List<Product> getProductByDiscountIdentifier_DiscountId(String discountId);
}
