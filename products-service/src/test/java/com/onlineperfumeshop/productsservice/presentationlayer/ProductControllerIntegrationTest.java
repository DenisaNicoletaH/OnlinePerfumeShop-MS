package com.onlineperfumeshop.productsservice.presentationlayer;

import com.onlineperfumeshop.productsservice.datalayer.Discount.DiscountIdentifier;
import com.onlineperfumeshop.productsservice.datalayer.Inventory.InventoryIdentifier;
import com.onlineperfumeshop.productsservice.datalayer.Product.Perfume;
import com.onlineperfumeshop.productsservice.datalayer.Product.ProductRepository;
import com.onlineperfumeshop.productsservice.datalayer.Product.Status;
import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql({"/data-mysql.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ProductRepository productRepository;

    private final String BASE_URI_PRODUCTS = "/api/v1/products";

    private final String VALID_PRODUCT_ID = "1bc9adfd-cd19-4d6e-9b62-f0e952569141";

    private final String VALID_DISCOUNT_ID = "672cf826-4d9d-41cd-a6b6-fa1815783b5f";

    private final String VALID_INVENTORY_ID = "d846a5a7-2e1c-4c79-809c-4f3f471e826d";

    private final String VALID_NAME = "Black Opium";

    private final String VALID_BRAND = "Yves Saint-Laurent";

    private final Double VALID_PRICES = 179.18;

    private final Status VALID_STATUS = Status.IN_STOCK;

    private final String VALID_SCENT_TYPE = "Warm";

    private final LocalDate VALID_DATE_PRODUCED = LocalDate.parse("2020-08-29");

    @Sql({"/data-mysql.sql"})
    @Test
    public void whenProductsExist_ThenReturnProducts() {

        Integer productsReturned = 10;
        Integer discountsReturned = 10;
        Integer inventoriesReturned = 10;

        webTestClient.get()
                .uri(BASE_URI_PRODUCTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(productsReturned)
                .jsonPath("$.length()").isEqualTo(discountsReturned)
                .jsonPath("$.length()").isEqualTo(inventoriesReturned);

    }

    @Test
    public void whenProductIdExists_ThenReturnProduct() {
        webTestClient.get()
                .uri(BASE_URI_PRODUCTS + "/" + VALID_PRODUCT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.productId").isEqualTo(VALID_PRODUCT_ID);

    }

    @Test
    public void whenUpdatedProductsWithValidValues_ThenReturnUpdatedProducts() {

        ProductRequestModel productRequestModel = newProductRequestModel(VALID_DISCOUNT_ID, VALID_INVENTORY_ID, VALID_NAME, VALID_BRAND, VALID_PRICES, VALID_STATUS, VALID_SCENT_TYPE, VALID_DATE_PRODUCED);
        webTestClient.put()
                .uri(BASE_URI_PRODUCTS + "/" + VALID_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(VALID_PRODUCT_ID)
                .jsonPath("$.discountId").isEqualTo(VALID_DISCOUNT_ID)
                .jsonPath("$.inventoryId").isEqualTo(VALID_INVENTORY_ID)
                .jsonPath("$.name").isEqualTo(VALID_NAME)
                .jsonPath("$.brand").isEqualTo(VALID_BRAND)
                .jsonPath("$.price").isEqualTo(VALID_PRICES)
                .jsonPath("$.status").isEqualTo(VALID_STATUS.toString())
                .jsonPath("$.scentType").isEqualTo(VALID_SCENT_TYPE)
                .jsonPath("$.dateProduced").isEqualTo(VALID_DATE_PRODUCED.toString());

    }


    //ConflictProductionException



    /*
    @Test
    public void whenCreatedProductsWithValidValues_ThenReturnNewProducts() {

        String expectedDiscountId = "0688f12c-8990-42c5-af0e-4c7ae767feff";
        String expectedInventoryId = "13b581aa-1fcf-4d80-b8ab-68a9c791a299";
        String expectedName = "Dior";
        String expectedBrand = "Miss Dior";
        Double expectedPrice = 70.50;
        Status status = Status.IN_STOCK;
        String expectedScentType = "Warm Floral";
        LocalDate expectedDateProduced = LocalDate.parse("2022-10-13");

        ProductRequestModel productRequestModel = newProductRequestModel(expectedDiscountId, expectedInventoryId, expectedName, expectedBrand, expectedPrice, status, expectedScentType, expectedDateProduced);

        webTestClient.post()
                .uri(BASE_URI_PRODUCTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(productRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductRequestModel.class)
                .value(val -> {
                    assertNotNull(val);
                    assertEquals(val.getDiscountId(), expectedDiscountId);
                    assertEquals(val.getInventoryId(),expectedInventoryId);
                    assertEquals(val.getName(), expectedName);
                    assertEquals(val.getBrand(), expectedBrand);
                    assertEquals(val.getPrice(), expectedPrice);
                    assertEquals(val.getStatus(), status);
                    assertEquals(val.getScentType(), expectedScentType);
                    assertEquals(val.getDateProduced(), expectedDateProduced);




                });

    }

     */

@Test
public void whenDeletedNotExistingProduct_ThenReturnNotFound(){

    webTestClient.delete()
            .uri(BASE_URI_PRODUCTS + "/" + "invalid_id")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound().expectBody().jsonPath("$").isNotEmpty();

}
/*

//addProductToInventory

@Test
public void whenCreatedProductWithValidValues_ThenAddInInventory() {

    String expectedInventoryId = "13b581aa-1fcf-4d80-b8ab-68a9c791a299";
    LocalDate expectedLastUpdated = LocalDate.parse("2018-11-19");

    String expectedDiscountId = "0688f12c-8990-42c5-af0e-4c7ae767feff";
    String expectedName = "Dior";
    String expectedBrand = "Miss Dior";
    Double expectedPrice = 70.50;
    Status status = Status.IN_STOCK;
    String expectedScentType = "Warm Floral";
    LocalDate expectedDateProduced = LocalDate.parse("2022-10-13");

    ProductRequestModel productRequestModel = newProductRequestModel(expectedDiscountId, VALID_INVENTORY_ID, expectedName, expectedBrand, expectedPrice, status, expectedScentType, expectedDateProduced);
    InventoryRequestModel inventoryRequestModel=newInventoryRequestModel(expectedLastUpdated);

    webTestClient.post()
            .uri(BASE_URI_PRODUCTS + "/" + expectedInventoryId + "/products")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(productRequestModel)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(ProductResponseModel.class)
            .value(val -> {
                assertNotNull(val);
                assertEquals(val.getProductId(), VALID_PRODUCT_ID);
                assertEquals(val.getDiscountId(), expectedDiscountId);
                assertEquals(val.getInventoryId(), expectedInventoryId);
                assertEquals(val.getName(), expectedName);
                assertEquals(val.getBrand(), expectedBrand);
                assertEquals(val.getPrice(), expectedPrice);
                assertEquals(val.getStatus(), status);
                assertEquals(val.getScentType(), expectedScentType);
                assertEquals(val.getDateProduced(), expectedDateProduced);


            });

}

//get all products in inventories
@Test
        public void whenProductsExistInInventoryId_ThenReturnProducts() {
    String expectedInventoryId="13b581aa-1fcf-4d80-b8ab-68a9c791a299";
    Integer productsReturned = 10;

    webTestClient.get()
            .uri(BASE_URI_PRODUCTS + "/" + VALID_INVENTORY_ID)
            .accept(MediaType.APPLICATION_JSON)
            .exchange().expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.length()").isEqualTo(VALID_PRODUCT_ID);


}

 */
    private ProductRequestModel newProductRequestModel(String discountId, String inventoryId, String name, String brand, Double price, Status status, String scentType, LocalDate dateProduced){

        return ProductRequestModel.builder()
                .discountId(discountId)
                .inventoryId(inventoryId)
                .name(name)
                .brand(brand)
                .price(price)
                .status(status.toString())
                .scentType(scentType)
                .dateProduced(dateProduced)
                .build();

    }

    private InventoryRequestModel newInventoryRequestModel(LocalDate lastUpdated) {

        return InventoryRequestModel.builder()
                .lastInventoryUpdated(lastUpdated).build();
    }


}