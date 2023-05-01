package com.onlineperfumeshop.deliveryservice.presentationlayer;

import com.onlineperfumeshop.deliveryservice.datalayer.DeliveryRepository;
import com.onlineperfumeshop.deliveryservice.datalayer.ShippingUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
class DeliveryControllerIntegrationTest {


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    DeliveryRepository deliveryRepository;

    private final String BASE_URI_DELIVERIES = "/api/v1/deliveries";

    private final String VALID_DELIVERY_ID="6eb13ffa-67fb-4bf7-aa44-a07050aa036a";
    private final String VALID_CHECKOUT_ID="bb56bd39-0104-43f8-9a3e-624ec898d48f";

    private final String VALID_Delivery_ID="c3540a89-cb47-4c96-888e-ff96708db4d8";
    private final String VALID_WAREHOUSE_LOCATION="22 Buena Vista Trail";
    private final String VALID_PHONE_NUMBER="683-562-5098";


    private final LocalDate VALID_ARRIVAL_TIME= LocalDate.parse("2023-06-21");

    private final String VALID_SHIPPING_UPDATE="'IN_DELIVERY";

    private final String VALID_STREET_ADDRESS="3 4th Terrace";

    private final String VALID_CITY="'Hearst";

    private final String VALID_PROVINCE="'Ontario";

    private final String VALID_COUNTRY="Canada";

    private final String VALID_POSTAL_CODE="'S4A 8Y2";

    private final String VALID_COUNTRY_CODE="ON";


    @Sql({"/data-mysql.sql"})
    @Test
    public void whenDeliveryExists_thenReturnDelivery() {

        Integer deliveryReturned = 10;
        Integer checkoutReturned=10;
        Integer clientReturned=10;

        webTestClient.get()
                .uri(BASE_URI_DELIVERIES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(deliveryReturned)
                .jsonPath("$.length()").isEqualTo(checkoutReturned)
                .jsonPath("$.length()").isEqualTo(clientReturned);

    }


    @Test
    public void whenDeliveryIdExists_thenReturnDelivery() {

        webTestClient.get()
                .uri(BASE_URI_DELIVERIES + "/" + VALID_DELIVERY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.deliveryId").isEqualTo(VALID_DELIVERY_ID);

    }

    @Test
    public void whenCreatedDeliveryWithValidValues_ThenReturnNewDelivery(){
        
    }






    private DeliveryRequestModel newDeliveryRequestModel (String warehouseLocation, String phoneNumber, LocalDate arrivalTime, ShippingUpdate shippingUpdate,String streetAddress,String city,String province,String country,String postalCode,String countryCode){


        return DeliveryRequestModel.builder()

                .warehouseLocation(warehouseLocation)
                .phoneNumber(phoneNumber)
                .arrivalTime(String.valueOf(arrivalTime))
                .shippingUpdate(String.valueOf(shippingUpdate))
                .streetAddress(streetAddress)
                .city(city)
                .province(province)
                .country(country)
                .postalCode(postalCode).build();
    }


}