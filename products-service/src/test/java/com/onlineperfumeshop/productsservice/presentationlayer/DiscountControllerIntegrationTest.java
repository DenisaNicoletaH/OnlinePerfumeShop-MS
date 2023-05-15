package com.onlineperfumeshop.productsservice.presentationlayer;

import com.onlineperfumeshop.productsservice.datalayer.Discount.DiscountRepository;
import com.onlineperfumeshop.productsservice.datalayer.Discount.SalePrices;
import com.onlineperfumeshop.productsservice.datalayer.Discount.SaleStatus;
import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountRequestModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql({"/data-mysql.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DiscountControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    DiscountRepository discountRepository;

    private final String BASE_URI_DISCOUNTS="/api/v1/discounts";
    private final String VALID_DISCOUNT_ID="672cf826-4d9d-41cd-a6b6-fa1815783b5f";

    private final String VALID_PRODUCT_ID = "1bc9adfd-cd19-4d6e-9b62-f0e952569141";


    private final Integer VALID_SALES_PERCENT=20;

    private final SalePrices VALID_NEW_PRICES=new SalePrices(122.32);

    private final SaleStatus VALID_SALES_STATUS=SaleStatus.SALE;
    @Sql({"/data-mysql.sql"})
    @Test
    public void whenDiscountExists_ThenReturnDiscounts(){
        Integer discountsReturned=10;

        webTestClient.get()
                .uri(BASE_URI_DISCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(discountsReturned);

    }

@Test
    public void whenDiscountIdExists_thenReturnDiscount(){
    webTestClient.get()
            .uri(BASE_URI_DISCOUNTS + "/"+ VALID_DISCOUNT_ID)
            .accept(MediaType.APPLICATION_JSON)
            .exchange().expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.discountId").isEqualTo(VALID_DISCOUNT_ID);

}




/*
    @Test
    public void whenCreatedDiscountWithValidValues_ThenReturnNewDiscount(){

        Integer expectedSalePercent=40;
        SalePrices expectedNewPrices= new SalePrices(40.00);
    SaleStatus expectedSaleStatus= SaleStatus.SALE;

    DiscountRequestModel discountRequestModel=newDiscountRequestModel(expectedSalePercent,expectedNewPrices,expectedSaleStatus);

    webTestClient.post()
            .uri(BASE_URI_DISCOUNTS)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(discountRequestModel)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(DiscountRequestModel.class)
            .value(val -> {
                assertNotNull(val);
                assertEquals(val.getSalePercent(), expectedSalePercent);
                assertEquals(val.getSalePrices().getNewPrices(), expectedNewPrices.getNewPrices());
                assertEquals(val.getSaleStatus(), expectedSaleStatus);



 */



    @Test
    public void whenDeletedNotExistingDiscount_ThenReturnNotFound() {
        webTestClient.delete()
                .uri(BASE_URI_DISCOUNTS + "/" + "invalid_id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound().expectBody().jsonPath("$").isNotEmpty();


    }

    //NOTFOUND EXCEPTION
    @Test
    public void  whenDiscountWithInvalidId_ThenReturnNotFound(){
        Integer expectedSalePercent=40;
        SalePrices expectedNewPrices=new SalePrices(30.00);
        SaleStatus expectedSaleStatus=SaleStatus.SALE;

        DiscountRequestModel discountRequestModel=newDiscountRequestModel(expectedSalePercent,expectedNewPrices,expectedSaleStatus);

        webTestClient.get()
                .uri(BASE_URI_DISCOUNTS + "/"+ "invalid_id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound().expectBody().jsonPath("$").isNotEmpty();

    }



private DiscountRequestModel newDiscountRequestModel(Integer salePercent,SalePrices salePrices, SaleStatus saleStatus){

        return DiscountRequestModel.builder()
                .salePercent(salePercent)
                .salePrices(salePrices)
                .saleStatus(saleStatus).build();
}



}