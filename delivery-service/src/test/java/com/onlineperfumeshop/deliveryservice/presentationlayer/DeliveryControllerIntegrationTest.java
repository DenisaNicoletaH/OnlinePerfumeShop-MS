package com.onlineperfumeshop.deliveryservice.presentationlayer;

import com.onlineperfumeshop.deliveryservice.datalayer.DeliveryRepository;
import com.onlineperfumeshop.deliveryservice.datalayer.ShippingUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
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
class DeliveryControllerIntegrationTest {


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    DeliveryRepository deliveryRepository;

    private final String BASE_URI_DELIVERIES = "/api/v1/deliveries";

    private final String VALID_DELIVERY_ID="6eb13ffa-67fb-4bf7-aa44-a07050aa036a";
    private final String VALID_CHECKOUT_ID="bb56bd39-0104-43f8-9a3e-624ec898d48f";

    private final String VALID_CLIENT_ID="c3540a89-cb47-4c96-888e-ff96708db4d8";
    private final String VALID_WAREHOUSE_LOCATION="22 Buena Vista Trail";
    private final String VALID_PHONE_NUMBER="683-562-5098";


    private final LocalDate VALID_ARRIVAL_TIME= LocalDate.parse("2023-06-21");

    private final ShippingUpdate VALID_SHIPPING_UPDATE= ShippingUpdate.IN_DELIVERY;

    private final String VALID_STREET_ADDRESS="3 4th Terrace";

    private final String VALID_CITY="'Hearst";

    private final String VALID_PROVINCE="'Ontario";

    private final String VALID_COUNTRY="Canada";

    private final String VALID_POSTAL_CODE="J5R 5J4";

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
    public void whenCreatedDeliveryWithValidValues_ThenReturnNewDelivery() {

        String expectedCheckoutId="bb56bd39-0104-43f8-9a3e-624ec898d48f";
        String expectedClientId="c3540a89-cb47-4c96-888e-ff96708db4d8";
        String expectedWarehouseLocation = "34th street";
        String expectedPhoneNumber = "514-342-2235";
        LocalDate expectedArrivalTime = LocalDate.parse("2020-04-15");
        ShippingUpdate expectedShippingUpdate = ShippingUpdate.IN_DELIVERY;
        String expectedStreetAddress = "5th cherryStreet";
        String expectedCity = "New York";
        String expectedProvince = "None";
        String expectedCountry = "USA";
        String expectedPostalCode = "J5R 5J4";
        String expectedCountryCode = "US";


        DeliveryRequestModel deliveryRequestModel = newDeliveryRequestModel(expectedCheckoutId,expectedClientId,expectedWarehouseLocation, expectedPhoneNumber, expectedArrivalTime, expectedShippingUpdate, expectedStreetAddress, expectedCity, expectedProvince, expectedCountry, expectedPostalCode, expectedCountryCode);

        webTestClient.post()
                .uri(BASE_URI_DELIVERIES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryResponseModel.class)
                .value(val -> {
                    assertNotNull(val);
                    assertEquals(val.getCheckoutId(), expectedCheckoutId);
                    assertEquals(val.getClientId(), expectedClientId);
                    assertEquals(val.getWarehouseLocation(), expectedWarehouseLocation);
                    assertEquals(val.getPhoneNumber(), expectedPhoneNumber);
                    assertEquals(val.getArrivalTime(), expectedArrivalTime);
                    assertEquals(val.getShippingUpdate(), expectedShippingUpdate.toString());
                    assertEquals(val.getStreetAddress(), expectedStreetAddress);
                    assertEquals(val.getCity(), expectedCity);
                    assertEquals(val.getProvince(), expectedProvince);
                    assertEquals(val.getCountry(), expectedCountry);
                    assertEquals(val.getPostalCode(), expectedPostalCode);
                    assertEquals(val.getCountryCode(), expectedCountryCode);


                });
    }
    @Test
    public void whenUpdatedDeliveriesWithValidValues_thenReturnUpdatedDeliveries() {

        String expectedPostalCode="J5R 5J4";

        DeliveryRequestModel deliveryRequestModel = newDeliveryRequestModel(VALID_CHECKOUT_ID,VALID_CLIENT_ID,VALID_WAREHOUSE_LOCATION,VALID_PHONE_NUMBER,VALID_ARRIVAL_TIME,VALID_SHIPPING_UPDATE,VALID_STREET_ADDRESS,VALID_CITY,VALID_PROVINCE,VALID_COUNTRY,expectedPostalCode,VALID_COUNTRY_CODE);
        webTestClient.put()
                .uri(BASE_URI_DELIVERIES + "/" + VALID_DELIVERY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.deliveryId").isEqualTo(VALID_DELIVERY_ID)
                .jsonPath("$.warehouseLocation").isEqualTo(VALID_WAREHOUSE_LOCATION)
                .jsonPath("$.phoneNumber").isEqualTo(VALID_PHONE_NUMBER)
                .jsonPath("$.arrivalTime").isEqualTo(VALID_ARRIVAL_TIME.toString())
                .jsonPath("$.shippingUpdate").isEqualTo(VALID_SHIPPING_UPDATE.toString())
                .jsonPath("$.streetAddress").isEqualTo(VALID_STREET_ADDRESS)
                .jsonPath("$.city").isEqualTo(VALID_CITY)
                .jsonPath("$.province").isEqualTo(VALID_PROVINCE)
                .jsonPath("$.country").isEqualTo(VALID_COUNTRY)
                .jsonPath("$.postalCode").isEqualTo(expectedPostalCode)
                .jsonPath("$.countryCode").isEqualTo(VALID_COUNTRY_CODE);



    }

    //PUT
    // NOTFOUND

    @Test
    public void whenUpdatedDeliveryWithInvalidId_ThenReturnNotFound(){
        String expectedCheckoutId="bb56bd39-0104-43f8-9a3e-624ec898d48f";
        String expectedClientId="c3540a89-cb47-4c96-888e-ff96708db4d8";
        String expectedWarehouseLocation = "34th street";
        String expectedPhoneNumber = "514-342-2235";
        LocalDate expectedArrivalTime = LocalDate.parse("2020-04-15");
        ShippingUpdate expectedShippingUpdate = ShippingUpdate.IN_DELIVERY;
        String expectedStreetAddress = "5th cherryStreet";
        String expectedCity = "New York";
        String expectedProvince = "None";
        String expectedCountry = "USA";
        String expectedPostalCode = "J5R 5J4";
        String expectedCountryCode = "US";


        DeliveryRequestModel deliveryRequestModel=newDeliveryRequestModel(expectedCheckoutId,expectedClientId,expectedWarehouseLocation,expectedPhoneNumber,expectedArrivalTime,expectedShippingUpdate,expectedStreetAddress,expectedCity,expectedProvince,expectedCountry,expectedPostalCode,expectedCountryCode);

        webTestClient.put()
                .uri(BASE_URI_DELIVERIES + "/"+ "invalid_id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();


    }


    //RECHECK
    //CONFLICT
    @Test
    public void whenUpdateDeliveryWithValidAddress_ThenReturnDelivery(){
        String invalidStreet="3 4th Terrace";

        DeliveryRequestModel deliveryRequestModel=newDeliveryRequestModel(VALID_CHECKOUT_ID,VALID_CLIENT_ID,VALID_WAREHOUSE_LOCATION,VALID_PHONE_NUMBER,VALID_ARRIVAL_TIME,VALID_SHIPPING_UPDATE,VALID_STREET_ADDRESS,VALID_CITY,VALID_PROVINCE,VALID_COUNTRY,VALID_POSTAL_CODE,VALID_COUNTRY_CODE);

        webTestClient.put()
                .uri(BASE_URI_DELIVERIES + "/" + VALID_DELIVERY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isNotEmpty();


    }

    @Test
    public void whenAddedDeliveryWIthInvalidPhoneValues_ThenReturnDelivery(){
        String expectedCheckoutId="bb56bd39-0104-43f8-9a3e-624ec898d48f";
        String expectedClientId="c3540a89-cb47-4c96-888e-ff96708db4d8";
        String expectedWarehouseLocation = "34th street";
        String invalidPhoneNumber = "+1 514-34sacea2-2235";
        LocalDate expectedArrivalTime = LocalDate.parse("2020-04-15");
        ShippingUpdate expectedShippingUpdate = ShippingUpdate.IN_DELIVERY;
        String expectedStreetAddress = "5th cherryStreet";
        String expectedCity = "New York";
        String expectedProvince = "None";
        String expectedCountry = "USA";
        String expectedPostalCode = "J5R 5J4";
        String expectedCountryCode = "US";

        DeliveryRequestModel deliveryRequestModel=newDeliveryRequestModel(expectedCheckoutId,expectedClientId,expectedWarehouseLocation,expectedCity,expectedArrivalTime,expectedShippingUpdate,invalidPhoneNumber,expectedStreetAddress,expectedProvince,expectedCountry,expectedPostalCode,expectedCountryCode);

        webTestClient.post()
                .uri(BASE_URI_DELIVERIES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();
    }

    @Test
    public void whenAddedDeliveryWIthInvalidPostalCodeValues_ThenReturnDelivery(){
        String expectedCheckoutId="bb56bd39-0104-43f8-9a3e-624ec898d48f";
        String expectedClientId="c3540a89-cb47-4c96-888e-ff96708db4d8";
        String expectedWarehouseLocation = "34th street";
        String expectedPhoneNumber = "514-342-2235";
        LocalDate expectedArrivalTime = LocalDate.parse("2020-04-15");
        ShippingUpdate expectedShippingUpdate = ShippingUpdate.IN_DELIVERY;
        String expectedStreetAddress = "5th cherryStreet";
        String expectedCity = "New York";
        String expectedProvince = "None";
        String expectedCountry = "USA";
        String invalidPostalCode = "12J5R 5J423fesd";
        String expectedCountryCode = "US";

        DeliveryRequestModel deliveryRequestModel=newDeliveryRequestModel(expectedCheckoutId,expectedClientId,expectedWarehouseLocation,expectedCity,expectedArrivalTime,expectedShippingUpdate,expectedPhoneNumber,expectedStreetAddress,expectedProvince,expectedCountry,invalidPostalCode,expectedCountryCode);

        webTestClient.post()
                .uri(BASE_URI_DELIVERIES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();


    }


        private DeliveryRequestModel newDeliveryRequestModel (String checkoutId,String clientId,String warehouseLocation, String phoneNumber, LocalDate arrivalTime, ShippingUpdate shippingUpdate,String streetAddress,String city,String province,String country,String postalCode,String countryCode){

        return DeliveryRequestModel.builder()

              .checkoutId(checkoutId)
               .clientId(clientId)
                .warehouseLocation(warehouseLocation)
                .phoneNumber(phoneNumber)
                .arrivalTime(String.valueOf(arrivalTime))
                .shippingUpdate(String.valueOf(shippingUpdate))
                .streetAddress(streetAddress)
                .city(city)
                .province(province)
                .country(country)
                .postalCode(postalCode)
                .countryCode(countryCode).build();
    }


}