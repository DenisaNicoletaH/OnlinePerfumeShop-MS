package com.onlineperfumeshop.checkoutservice.presentationlayer;


import com.onlineperfumeshop.checkoutservice.datalayer.Checkout;
import com.onlineperfumeshop.checkoutservice.datalayer.CheckoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql({"/data-mysql.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CheckoutControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    CheckoutRepository checkoutRepository;
    private final String BASE_URI_CHECKOUTS = "/api/v1/checkouts";
    private final String VALID_CHECKOUTS_ID = "bb56bd39-0104-43f8-9a3e-624ec898d48f";
    private final String VALID_PRODUCTS_ID = "1bc9adfd-cd19-4d6e-9b62-f0e952569141";
    private final String VALID_INVENTORY_ID = "bb56bd39-0104-43f8-9a3e-624ec898d48f";

    private final Double VALID_AMOUNT = 54.43;

    private final Double VALID_TAXES =  7.96;

    private final Double VALID_SHIPPING = 15.99;

    private final LocalDate VALID_END_OF_SALE = LocalDate.parse("2022-07-30");

    private final String VALID_PAYMENT_TYPE = "mastercard";

    private final Double VALID_TOTAL_AMOUNT=835.79;

    @Sql({"/data-mysql.sql"})
    @Test
    public void whenCheckoutsExists_thenReturnCheckouts() {

        Integer checkoutsReturned = 10;
        Integer productsReturned = 10;

        webTestClient.get()
                .uri(BASE_URI_CHECKOUTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(checkoutsReturned)
                .jsonPath("$.length()").isEqualTo(productsReturned);

    }
    @Test
    public void whenCheckoutIdExists_thenReturnCheckout() {
        webTestClient.get()
                .uri(BASE_URI_CHECKOUTS + "/"+VALID_CHECKOUTS_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.checkoutId").isEqualTo(VALID_CHECKOUTS_ID);

    }

    @Test
    public void whenCreatedCheckoutWithValidValues_ThenReturnNewCheckout(){

        Double expectedAmount=30.00;
        Double expectedTaxes=15.00;
        Double expectedShipping=20.00;
        LocalDate expectedEndOfSale= LocalDate.parse("2023-04-05");
        String expectedPaymentType="mastercard";
        Double expectedTotalAmount=100.00;


        CheckoutRequestModel checkoutRequestModel= newCheckoutRequestModel(expectedAmount,expectedTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);


        webTestClient.post()
                .uri(BASE_URI_CHECKOUTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CheckoutResponseModel.class)
                .value(val -> {
                    assertNotNull(val);
                    assertEquals(val.getAmount(), expectedAmount);
                    assertEquals(val.getTaxes(), expectedTaxes);
                    assertEquals(val.getShipping(), expectedShipping);
                    assertEquals(val.getEndOfSaleDate(), expectedEndOfSale);
                    assertEquals(val.getPaymentType(), expectedPaymentType);
                    assertEquals(val.getTotalAmount(), expectedTotalAmount);


                });
    }

    private CheckoutRequestModel newCheckoutRequestModel (Double amount, Double taxes,Double shipping,LocalDate endOfSale,String paymentType,Double totalAmount) {

        return CheckoutRequestModel.builder()

                .amount(amount)
                .taxes(taxes)
                .shipping(shipping)
                .endOfSaleDate(endOfSale)
                .paymentType(paymentType)
                .totalAmount(totalAmount).build();

    }

    @Test
    public void whenUpdatedCheckoutWithValidValues_thenReturnUpdatedCheckout(){

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(VALID_AMOUNT,VALID_TAXES,VALID_SHIPPING,VALID_END_OF_SALE,VALID_PAYMENT_TYPE,VALID_TOTAL_AMOUNT);
        webTestClient.put()
                .uri(BASE_URI_CHECKOUTS + "/" + VALID_CHECKOUTS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.checkoutId").isEqualTo(VALID_CHECKOUTS_ID)
                .jsonPath("$.amount").isEqualTo(VALID_AMOUNT)
                .jsonPath("$.taxes").isEqualTo(VALID_TAXES)
                .jsonPath("$.shipping").isEqualTo(VALID_SHIPPING)
                .jsonPath("$.endOfSaleDate").isEqualTo(VALID_END_OF_SALE.toString())
                .jsonPath("$.paymentType").isEqualTo(VALID_PAYMENT_TYPE)
                .jsonPath("$.totalAmount").isEqualTo(VALID_TOTAL_AMOUNT);



    }

    @Test
    public void whenDeletedNotExistingCheckout_ThenReturnNotFound(){

        //act
        webTestClient.delete()
                .uri(BASE_URI_CHECKOUTS + "/" + "invalid_id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound().expectBody().jsonPath("$").isNotEmpty();


    }

    //exceptions

    //POST
    //Amount
    @Test
    public void whenAddedCheckoutWithAmountInvalidValues_ThenReturnNewClient(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxAmount=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(invalidAmount,expectedTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);

        webTestClient.post()
                .uri(BASE_URI_CHECKOUTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();



    }

    @Test
    public void whenAddedCheckoutWithTaxesInvalidValues_ThenReturnNewClient(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxes=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedAmount=10.00;
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(expectedAmount,invalidTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);

        webTestClient.post()
                .uri(BASE_URI_CHECKOUTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();

    }
    @Test
    public void whenAddedCheckoutWithShippingInvalidValues_ThenReturnNewClient(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxes=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedAmount=10.00;
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(expectedAmount,expectedTaxes,invalidShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);

        webTestClient.post()
                .uri(BASE_URI_CHECKOUTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();

    }
    @Test
    public void whenAddedCheckoutWithTotalAmountInvalidValues_ThenReturnNewClient(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxes=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedAmount=10.00;
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(expectedAmount,expectedTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,invalidTotalAmount);

        webTestClient.post()
                .uri(BASE_URI_CHECKOUTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();

    }


    //PUT
    @Test
    public void whenUpdatedCheckoutWithAmountInvalidValues_ThenReturnNewClient(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxAmount=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(invalidAmount,expectedTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);

        webTestClient.put()
                .uri(BASE_URI_CHECKOUTS + "/"+ VALID_CHECKOUTS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();



    }

    @Test
    public void whenUpdatedCheckoutWithTaxesInvalidValues_ThenReturnNewClient(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxes=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedAmount=10.00;
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(expectedAmount,invalidTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);

        webTestClient.put()
                .uri(BASE_URI_CHECKOUTS + "/"+ VALID_CHECKOUTS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();

    }
    @Test
    public void whenUpdatedCheckoutWithShippingInvalidValues_ThenReturnNewClient(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxes=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedAmount=10.00;
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(expectedAmount,expectedTaxes,invalidShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);

        webTestClient.put()
                .uri(BASE_URI_CHECKOUTS + "/"+ VALID_CHECKOUTS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();

    }
    @Test
    public void whenUpdatedCheckoutWithTotalAmountInvalidValues_ThenReturnNewClient(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxes=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedAmount=10.00;
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(expectedAmount,expectedTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,invalidTotalAmount);

        webTestClient.put()
                .uri(BASE_URI_CHECKOUTS + "/"+ VALID_CHECKOUTS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();

    }


    //If Id is not found

    //PUT
    @Test
    public void whenUpdatedCheckoutWithInvalidId_ThenReturnNotFound(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxes=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedAmount=10.00;
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(expectedAmount,expectedTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);

        webTestClient.put()
                .uri(BASE_URI_CHECKOUTS + "/"+ "invalid_id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(checkoutRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();

    }

    //DELETE
    @Test
    public void whenDeletedCheckoutWithInvalidId_ThenReturnNotFound(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxes=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedAmount=10.00;
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(expectedAmount,expectedTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);

        webTestClient.delete()
                .uri(BASE_URI_CHECKOUTS + "/"+ "invalid_id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound().expectBody().jsonPath("$").isNotEmpty();

    }

    @Test
    public void whenGetCheckoutWithInvalidId_ThenReturnNotFound(){
        Double invalidAmount= -100.30;
        Double invalidTotalAmount= -200.00;
        Double invalidShipping=-25.99;
        Double invalidTaxes=-5.00;

        String expectedProductId="1bc9adfd-cd19-4d6e-9b62-f0e952569141";
        Double expectedAmount=10.00;
        Double expectedTaxes=3.00;
        Double expectedShipping=25.00;
        LocalDate expectedEndOfSale=LocalDate.parse("2022-05-12");
        String expectedPaymentType="visa";
        Double expectedTotalAmount=28.00;

        CheckoutRequestModel checkoutRequestModel=newCheckoutRequestModel(expectedAmount,expectedTaxes,expectedShipping,expectedEndOfSale,expectedPaymentType,expectedTotalAmount);

        webTestClient.get()
                .uri(BASE_URI_CHECKOUTS + "/"+ "invalid_id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound().expectBody().jsonPath("$").isNotEmpty();

    }







}