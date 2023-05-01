package com.onlineperfumeshop.productsservice.datalayer;


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
        String expectedName="Sugary Lemon";
        String expectedBrand="Replica";
        Double expectedPrice=350.00;
        Status expectedStatus=Status.IN_STOCK;
        String expectedScentType="Floral";
        LocalDate expectedDateProduced= LocalDate.parse("2023-04-12");

        presavedProduct=productRepository.save(new Product(expectedName,expectedBrand,expectedPrice,expectedStatus,expectedScentType,expectedDateProduced));

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
    public void findByProductOIdentifier_ProductId_ShouldReturnNull(){

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


        Product savedProduct=productRepository.save(new Product(expectedName,expectedBrand,expectedPrice,expectedStatus,expectedScentType,expectedDateProduced));

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
