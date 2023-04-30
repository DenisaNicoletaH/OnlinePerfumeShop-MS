package com.onlineperfumeshop.checkoutservice.presentationlayer;


import com.onlineperfumeshop.checkoutservice.datalayer.Checkout;
import com.onlineperfumeshop.checkoutservice.datalayer.CheckoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    @Sql({"/data-mysql.sql"})
    @Test
    public void whenCheckoutsExists_thenReturnClient() {

        Integer checkoutsReturned = 10;
        Integer productsReturned = 10;
        Integer inventoryReturned = 10;



        webTestClient.get()
                .uri(BASE_URI_CHECKOUTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.length()").isEqualTo(checkoutsReturned)
                .jsonPath("$.length()").isEqualTo(productsReturned)
                .jsonPath("$.length()").isEqualTo(inventoryReturned);
    }
    @Test
    public void whenCheckoutIdExists_thenReturnCheckout() {

        Integer checkoutsReturned = 10;
        Integer productsReturned = 10;
        Integer inventoryReturned = 10;



        webTestClient.get()
                .uri(BASE_URI_CHECKOUTS + "/"+VALID_CHECKOUTS_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.checkoutId").isEqualTo(VALID_CHECKOUTS_ID);

    }


    private CheckoutRequestModel createNewCheckoutRequestModel(String checkoutId, String productId, String, String inventoryId, Double amount, Double taxes, Double shipping, LocalDate end_of_sale,String payment_type,Double totalAmount){
        Option firstCheckout=new Option("option 1");
        Option secondCheckout=new Option("option 2");

        List<Option> optionList = new ArrayList<>(Arrays.asList(firstCheckout,secondCheckout));

        CheckoutRequestModel checkoutRequestModel= CheckoutRequestModel.builder().amount(203.00).currentDate()
    }
}
