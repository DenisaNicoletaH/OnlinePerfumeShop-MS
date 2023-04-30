package com.onlineperfumeshop.productsservice.datalayer.Product;


import com.onlineperfumeshop.productsservice.datalayer.Discount.DiscountIdentifier;
import com.onlineperfumeshop.productsservice.datalayer.Inventory.InventoryIdentifier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "products")
@Data
public class Product {


    //product id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;


    @Embedded
    private ProductIdentifier productIdentifier;


    @Embedded
    private InventoryIdentifier inventoryIdentifier;

    @Embedded
    private DiscountIdentifier discountIdentifier;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String name;

    private Double price;

    private String brand;
    @Embedded
    private Perfume perfume;

    public Product() {
        this.productIdentifier = new ProductIdentifier();
    }

    public Product(String name, Double price, String brand, Date dateProduced, String scentType,Status status,InventoryIdentifier inventoryIdentifier,DiscountIdentifier discountIdentifier) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.status=status;
        this.inventoryIdentifier=inventoryIdentifier;
        this.discountIdentifier=discountIdentifier;
        this.perfume = new Perfume(scentType,dateProduced);
    }




    public @NotNull String getName() {
        return name;
    }
    public @NotNull Double getPrice() {
        return price;
    }
    public @NotNull String getBrand() {
        return brand;
    }
    public @NotNull Status getStatus() {
        return status;
    }






}
