package com.onlineperfumeshop.productsservice.presentationlayer;

import com.onlineperfumeshop.productsservice.Utils.HttpErrorInfo;
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
import org.springframework.http.HttpStatus;
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
    private final String BASE_URI_DISCOUNTS="/api/v1/discounts";

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
    //return products by discountId
    @Test
    public void whenDiscountIdExists_thenReturnProduct() {
        Integer productsReturned = 4;

        webTestClient.get()
                .uri(BASE_URI_DISCOUNTS + "/" + VALID_DISCOUNT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.discountId").isEqualTo(VALID_DISCOUNT_ID)
                .jsonPath("$.length()").isEqualTo(productsReturned);

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

    @Test
    public void whenUpdatedProductWithoutValidId_thenThrowNotFound(){


        ProductRequestModel productRequestModel = newProductRequestModel(VALID_DISCOUNT_ID, VALID_INVENTORY_ID, VALID_NAME, VALID_BRAND, VALID_PRICES, VALID_STATUS, VALID_SCENT_TYPE, VALID_DATE_PRODUCED);
        webTestClient.put()
                .uri(BASE_URI_PRODUCTS + "/products/"  + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productRequestModel).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(HttpErrorInfo.class);

    }





@Test
public void whenDeletedNotExistingProduct_ThenReturnNotFound(){

    webTestClient.delete()
            .uri(BASE_URI_PRODUCTS + "/" + "invalid_id")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound().expectBody().jsonPath("$").isNotEmpty();

}


//get all products in inventories
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