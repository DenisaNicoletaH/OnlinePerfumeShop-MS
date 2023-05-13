package com.onlineperfumeshop.productsservice.datalayer;


import com.onlineperfumeshop.productsservice.datalayer.Discount.DiscountIdentifier;
import com.onlineperfumeshop.productsservice.datalayer.Inventory.Inventory;
import com.onlineperfumeshop.productsservice.datalayer.Inventory.InventoryIdentifier;
import com.onlineperfumeshop.productsservice.datalayer.Product.Perfume;
import com.onlineperfumeshop.productsservice.datalayer.Product.Product;
import com.onlineperfumeshop.productsservice.datalayer.Product.ProductRepository;
import com.onlineperfumeshop.productsservice.datalayer.Product.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class ProductPersistenceTest {

    private Product presavedProduct;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    public void setup(){
        productRepository.deleteAll();
        String name="Sugary Lemon";
        String brand="Replica";
        Double price=350.00;
        InventoryIdentifier inventoryIdentifier=new InventoryIdentifier();
        DiscountIdentifier discountIdentifier=new DiscountIdentifier();
        String inventoryId="d846a5a7-2e1c-4c79-809c-4f3f471e826d";
        String discountId="672cf826-4d9d-41cd-a6b6-fa1815783b5f";
        Status status=Status.IN_STOCK;
        String scentType="Floral";
        LocalDate dateProduced= LocalDate.parse("2023-04-12");

        presavedProduct=productRepository.save(new Product(name,price,brand,dateProduced,scentType,status,new InventoryIdentifier(inventoryId),new DiscountIdentifier(discountId)));

    }

    //get by id
    @Test
    public void findByProductIdentifier_ProductId_ShouldSucceed(){

        //act
        Product found=productRepository.findByProductIdentifier_ProductId(presavedProduct.getProductIdentifier().getProductId());

        //assert
        assertNotNull(found);
        assertThat(presavedProduct,samePropertyValuesAs(found));

    }


    //if id is invalid return null
    @Test
    public void findByProductIdentifier_ProductId_ShouldReturnNull(){

        //act
        Product found=productRepository.findByProductIdentifier_ProductId(presavedProduct.getProductIdentifier().getProductId() +1);

        //assert
        assertNull(found);

    }


    @Test
    public void saveNewProduct_shouldSucceed(){
        //arrange
        String expectedName="Flower";
        String expectedBrand="TestBrand";
        Double expectedPrice=200.00;
        Status expectedStatus=Status.IN_STOCK;
        String expectedScentType="Lemon";
        LocalDate expectedDateProduced= LocalDate.parse("2013-05-10");


        Product savedProduct=productRepository.save(new Product(expectedName,expectedPrice,expectedBrand,expectedDateProduced,expectedScentType,expectedStatus, new InventoryIdentifier("d846a5a7-2e1c-4c79-809c-4f3f471e826d"), new DiscountIdentifier("672cf826-4d9d-41cd-a6b6-fa1815783b5f")));

        //assert
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());

        assertNotNull(savedProduct.getProductIdentifier().getProductId());

        assertEquals(expectedName,savedProduct.getName());
        assertEquals(expectedBrand,savedProduct.getBrand());
        assertEquals(expectedPrice,savedProduct.getPrice());
        assertEquals(expectedStatus,savedProduct.getStatus());
        assertEquals(expectedScentType,savedProduct.getPerfume().getScentType());
        assertEquals(expectedDateProduced,savedProduct.getPerfume().getDateProduced());
    }


    @Test
    public void deleteProductWithValidIdentifier_ShouldSucceed(){
        productRepository.delete(presavedProduct);

        Product found=productRepository.findByProductIdentifier_ProductId(presavedProduct.getProductIdentifier().getProductId());

        assertNull(found);
    }

    @Test
    public void updateProduct_ShouldSucceed(){
        //assert
        String updatedBrand="Armani";

        presavedProduct.setBrand(updatedBrand);
        //act
        Product savedProduct=productRepository.save(presavedProduct);

        //assert
        assertNotNull(savedProduct);
        assertThat(savedProduct,samePropertyValuesAs(presavedProduct));

    }


}
