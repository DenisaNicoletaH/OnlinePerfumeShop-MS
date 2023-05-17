package com.onlineperfumeshop.deliveryservice.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onlineperfumeshop.deliveryservice.datalayer.*;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Checkout.CheckoutResponseModel;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Clients.ClientResponseModel;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Products.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@Slf4j
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DeliveryControllerIntegrationTest {


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    RestTemplate restTemplate;

    ObjectMapper mapper=new ObjectMapper();

    private MockRestServiceServer mockRestServiceServer;


    @BeforeEach
    public void setup(){
        mockRestServiceServer=MockRestServiceServer.createServer(restTemplate);
        mapper.registerModule(new JavaTimeModule());

    }

    @Autowired
    DeliveryRepository deliveryRepository;

    private final String BASE_URI_DELIVERIES = "/api/v1/deliveries";

    private final String BASE_URI_PRODUCTS="/api/v1/products";

    private final String VALID_DELIVERY_ID="6eb13ffa-67fb-4bf7-aa44-a07050aa036a";
    private final String VALID_CHECKOUT_ID="bb56bd39-0104-43f8-9a3e-624ec898d48f";

    private final String VALID_CLIENT_ID="c3540a89-cb47-4c96-888e-ff96708db4d8";
    private final String VALID_WAREHOUSE_LOCATION="22 Buena Vista Trail";
    private final String VALID_PHONE_NUMBER="683-562-5098";


    private final LocalDate VALID_ARRIVAL_TIME= LocalDate.parse("2023-06-21");

    private final ShippingUpdate VALID_SHIPPING_UPDATE= ShippingUpdate.IN_DELIVERY;

    private final String VALID_STREET_ADDRESS="3 4th Terrace";

    private final String VALID_CITY="'Hearst";

    private final String VALID_PROVINCE="Ontario";

    private final String VALID_COUNTRY="Canada";

    private final String VALID_POSTAL_CODE="J5R 5J4";

    private final String VALID_COUNTRY_CODE="ON";


    private final String VALID_PRODUCT_ID="b3af9b91-db94-4e9e-ae63-aa382b442eb3";

    private final String VALID_INVENTORY_ID="fd82210a-27c9-401f-bcba-9878e1a3d880";

    private final String VALID_DISCOUNT_ID="282b6579-2f3d-4758-b7b8-f6ddd838ad8e";



    @Test
    public void whenDeliveryExists_thenReturnDelivery() {

        Integer deliveryReturned = 1;
        Integer checkoutReturned=1;
        Integer clientReturned=1;

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
    public void whenDeliveryIdExists_thenReturnDelivery() throws JsonProcessingException, URISyntaxException {

        CheckoutResponseModel checkoutResponseModel=new CheckoutResponseModel(VALID_CHECKOUT_ID,100.00,5.00,15.00,LocalDate.parse("2023-05-04"),120.00,
                "b3af9b91-db94-4e9e-ae63-aa382b442eb3","mastercard");


        DeliveryRequestModel deliveryRequestModel = newDeliveryRequestModel(VALID_CHECKOUT_ID,VALID_CLIENT_ID,VALID_PRODUCT_ID,VALID_DISCOUNT_ID,VALID_INVENTORY_ID,VALID_WAREHOUSE_LOCATION,VALID_PHONE_NUMBER,VALID_ARRIVAL_TIME,VALID_SHIPPING_UPDATE,VALID_STREET_ADDRESS,VALID_CITY,VALID_COUNTRY,VALID_PROVINCE,VALID_POSTAL_CODE,VALID_COUNTRY_CODE);


        ProductResponseModel productResponseModel=new ProductResponseModel(VALID_PRODUCT_ID,VALID_DISCOUNT_ID,VALID_INVENTORY_ID,"Flowerbomb",
                "Viktor&Rolf",150.00,"IN_STOCK","Floral",LocalDate.parse("2018-05-09"));


        ClientResponseModel clientResponseModel=new ClientResponseModel(
                VALID_CLIENT_ID,
                "Denisa","Hategan","denisahategan@gmail.com","135 Place Cote Vertu",
                "Montreal","Quebec","Canada","H4N 1G4","514-291-9304","QC");



        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7001/api/v1/clients/" + VALID_CLIENT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(clientResponseModel))
                );

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7003/api/v1/checkouts/" + VALID_CHECKOUT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(checkoutResponseModel))
                );


        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7002/api/v1/products/" + VALID_PRODUCT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(productResponseModel))
                );




        webTestClient.get()
                .uri(BASE_URI_DELIVERIES + "/" + VALID_DELIVERY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.deliveryId").isEqualTo(VALID_DELIVERY_ID);

    }

    @Test
    public void whenCreatedDeliveryWithValidValues_ThenReturnNewDelivery() throws JsonProcessingException, URISyntaxException {

        String expectedCheckoutId="bb56bd39-0104-43f8-9a3e-624ec898d48f";
        String expectedClientId="c3540a89-cb47-4c96-888e-ff96708db4d8";
        String expectedProductId="b3af9b91-db94-4e9e-ae63-aa382b442eb3";
        String expectedDiscountId="282b6579-2f3d-4758-b7b8-f6ddd838ad8e";
        String expectedInventoryId="fd82210a-27c9-401f-bcba-9878e1a3d880";

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


        DeliveryRequestModel deliveryRequestModel = newDeliveryRequestModel(expectedCheckoutId,expectedClientId,expectedProductId,expectedDiscountId,expectedInventoryId,expectedWarehouseLocation, expectedPhoneNumber, expectedArrivalTime, expectedShippingUpdate, expectedStreetAddress, expectedCity, expectedProvince, expectedCountry, expectedPostalCode, expectedCountryCode);



        CheckoutResponseModel checkoutResponseModel=new CheckoutResponseModel(VALID_CHECKOUT_ID,100.00,5.00,15.00,LocalDate.parse("2023-05-04"),120.00,
                "b3af9b91-db94-4e9e-ae63-aa382b442eb3","mastercard");


       // DeliveryRequestModel deliveryRequestModel1 = newDeliveryRequestModel(VALID_CHECKOUT_ID,VALID_CLIENT_ID,VALID_PRODUCT_ID,VALID_DISCOUNT_ID,VALID_INVENTORY_ID,VALID_WAREHOUSE_LOCATION,VALID_PHONE_NUMBER,VALID_ARRIVAL_TIME,VALID_SHIPPING_UPDATE,VALID_STREET_ADDRESS,VALID_CITY,VALID_COUNTRY,VALID_PROVINCE,VALID_POSTAL_CODE,VALID_COUNTRY_CODE);


        ProductResponseModel productResponseModel=new ProductResponseModel(VALID_PRODUCT_ID,VALID_DISCOUNT_ID,VALID_INVENTORY_ID,"Flowerbomb",
                "Viktor&Rolf",150.00,"IN_STOCK","Floral",LocalDate.parse("2018-05-09"));


        ClientResponseModel clientResponseModel=new ClientResponseModel(
                VALID_CLIENT_ID,
                "Denisa","Hategan","denisahategan@gmail.com","135 Place Cote Vertu",
                "Montreal","Quebec","Canada","H4N 1G4","514-291-9304","QC");



        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7001/api/v1/clients/" + VALID_CLIENT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(clientResponseModel))
                );

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7003/api/v1/checkouts/" + VALID_CHECKOUT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(checkoutResponseModel))
                );


        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7002/api/v1/products/" + VALID_PRODUCT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(productResponseModel))
                );
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
    public void whenUpdatedDeliveriesWithValidValues_thenReturnUpdatedDeliveries() throws URISyntaxException, JsonProcessingException {

        String expectedPostalCode="J5R 5J4";

        String expectedDeliveryId="6eb13ffa-67fb-4bf7-aa44-a07050aa036a";

        CheckoutResponseModel checkoutResponseModel=new CheckoutResponseModel(VALID_CHECKOUT_ID,100.00,5.00,15.00,LocalDate.parse("2023-05-04"),120.00,
                "b3af9b91-db94-4e9e-ae63-aa382b442eb3","mastercard");


        DeliveryRequestModel deliveryRequestModel = newDeliveryRequestModel(VALID_CHECKOUT_ID,VALID_CLIENT_ID,VALID_PRODUCT_ID,VALID_DISCOUNT_ID,VALID_INVENTORY_ID,VALID_WAREHOUSE_LOCATION,VALID_PHONE_NUMBER,VALID_ARRIVAL_TIME,VALID_SHIPPING_UPDATE,VALID_STREET_ADDRESS,VALID_CITY,VALID_COUNTRY,VALID_PROVINCE,VALID_POSTAL_CODE,VALID_COUNTRY_CODE);



        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7003/api/v1/checkouts/" + VALID_CHECKOUT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(checkoutResponseModel))
                );

        ProductResponseModel productResponseModel=new ProductResponseModel(VALID_PRODUCT_ID,VALID_DISCOUNT_ID,VALID_INVENTORY_ID,"Flowerbomb",
                "Viktor&Rolf",150.00,"IN_STOCK","Floral",LocalDate.parse("2018-05-09"));

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7002/api/v1/products/" + VALID_PRODUCT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(productResponseModel))
                );


        ClientResponseModel clientResponseModel=new ClientResponseModel(
                VALID_CLIENT_ID,
                "Denisa","Hategan","denisahategan@gmail.com","135 Place Cote Vertu",
                "Montreal","Quebec","Canada","H4N 1G4","514-291-9304","QC");

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7001/api/v1/clients/" + VALID_CLIENT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(clientResponseModel))
                );




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
                .jsonPath("$.country").isEqualTo(VALID_COUNTRY)
                .jsonPath("$.province").isEqualTo(VALID_PROVINCE)
                .jsonPath("$.postalCode").isEqualTo(VALID_POSTAL_CODE)
                .jsonPath("$.countryCode").isEqualTo(VALID_COUNTRY_CODE);

}
    //PUT
    // NOTFOUND
/*
    @Test
    public void whenUpdatedDeliveryWithInvalidId_ThenReturnNotFound(){
        String expectedCheckoutId="bb56bd39-0104-43f8-9a3e-624ec898d48f";
        String expectedClientId="c3540a89-cb47-4c96-888e-ff96708db4d8";
        String expectedProductId="b3af9b91-db94-4e9e-ae63-aa382b442eb3";
        String expectedDiscountId="282b6579-2f3d-4758-b7b8-f6ddd838ad8e";
        String expectedInventoryId="fd82210a-27c9-401f-bcba-9878e1a3d880";

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


        DeliveryRequestModel deliveryRequestModel=newDeliveryRequestModel(expectedCheckoutId,expectedClientId,expectedProductId,expectedDiscountId,expectedInventoryId,expectedWarehouseLocation,expectedPhoneNumber,expectedArrivalTime,expectedShippingUpdate,expectedStreetAddress,expectedCity,expectedProvince,expectedCountry,expectedPostalCode,expectedCountryCode);

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
*/
    @Test
    public void whenAddedDeliveryWIthInvalidPhoneValues_ThenReturnDelivery(){
        String expectedCheckoutId="bb56bd39-0104-43f8-9a3e-624ec898d48f";
        String expectedClientId="c3540a89-cb47-4c96-888e-ff96708db4d8";
        String expectedProductId="b3af9b91-db94-4e9e-ae63-aa382b442eb3";
        String expectedDiscountId="282b6579-2f3d-4758-b7b8-f6ddd838ad8e";
        String expectedInventoryId="fd82210a-27c9-401f-bcba-9878e1a3d880";

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

        DeliveryRequestModel deliveryRequestModel=newDeliveryRequestModel(expectedCheckoutId,expectedClientId,expectedProductId,expectedDiscountId,expectedInventoryId,expectedWarehouseLocation,expectedCity,expectedArrivalTime,expectedShippingUpdate,invalidPhoneNumber,expectedStreetAddress,expectedProvince,expectedCountry,expectedPostalCode,expectedCountryCode);

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

        String expectedProductId="b3af9b91-db94-4e9e-ae63-aa382b442eb3";
        String expectedDiscountId="282b6579-2f3d-4758-b7b8-f6ddd838ad8e";
        String expectedInventoryId="fd82210a-27c9-401f-bcba-9878e1a3d880";

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

        DeliveryRequestModel deliveryRequestModel=newDeliveryRequestModel(expectedCheckoutId,expectedClientId,expectedProductId,expectedDiscountId,expectedInventoryId,expectedWarehouseLocation,expectedCity,expectedArrivalTime,expectedShippingUpdate,expectedPhoneNumber,expectedStreetAddress,expectedProvince,expectedCountry,invalidPostalCode,expectedCountryCode);

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
    public void getAllFieldsAreValid_ShouldReturnField() throws URISyntaxException, JsonProcessingException {
       //arrange
        DeliveryRequestModel deliveryRequestModel= DeliveryRequestModel.builder()
                .discountId("282b6579-2f3d-4758-b7b8-f6ddd838ad8e")
                .inventoryId("fd82210a-27c9-401f-bcba-9878e1a3d880")
                .checkoutId("bb56bd39-0104-43f8-9a3e-624ec898d48f")
                .productId("b3af9b91-db94-4e9e-ae63-aa382b442eb3")
                .postalCode("H4N 1G4")
                .country("Canada")
                .province("Quebec")
                .city("Montreal")
                .streetAddress("3rd Rainbow Road")
                .shippingUpdate(String.valueOf(ShippingUpdate.DELIVERED))
                .arrivalTime("2023-10-12")
                .phoneNumber("514-432-1232")
                .warehouseLocation("4th Ghost Road")
                .countryCode("CA")
                .build();

        //required for get request in CLientSerivceClient
        String clientId="c3540a89-cb47-4c96-888e-ff96708db4d8";

        ClientResponseModel clientResponseModel=new ClientResponseModel(
                clientId,
                "Denisa","Hategan","denisahategan@gmail.com","135 Place Cote Vertu",
                "Montreal","Quebec","Canada","H4N 1G4","514-291-9304","QC");

        mockRestServiceServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:7002/api/v1/clients/" + clientId)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(clientResponseModel)));


        //CheckoutServiceCLient

        CheckoutResponseModel checkoutResponseModel=new CheckoutResponseModel("bb56bd39-0104-43f8-9a3e-624ec898d48f",100.00,5.00,15.00,LocalDate.parse("2023-05-04"),120.00,
                "b3af9b91-db94-4e9e-ae63-aa382b442eb3","mastercard");

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7003/api/v1/checkouts/bb56bd39-0104-43f8-9a3e-624ec898d48f")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(checkoutResponseModel))
                );


        //DiscountServiceClient
        String discountId="282b6579-2f3d-4758-b7b8-f6ddd838ad8e";
        SalePrices salePrices=new SalePrices(40.00);
        DiscountResponseModel discountResponseModel=new DiscountResponseModel(discountId,20, SaleStatus.SALE,salePrices);


        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7003/api/v1/discounts/" + discountId)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(discountResponseModel))
                );


        //InventoryServiceClient
String inventoryId="fd82210a-27c9-401f-bcba-9878e1a3d880";
        InventoryResponseModel inventoryResponseModel=new InventoryResponseModel(inventoryId,LocalDate.parse("2022-06-05"));

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7003/api/v1/inventories/" + inventoryId)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(inventoryResponseModel))
                );


        //ProductServiceClient
        String productId="b3af9b91-db94-4e9e-ae63-aa382b442eb3";

      //  String discountId="282b6579-2f3d-4758-b7b8-f6ddd838ad8e";
      //  String inventoryId="fd82210a-27c9-401f-bcba-9878e1a3d880";
        ProductResponseModel productResponseModel=new ProductResponseModel(productId,discountId,inventoryId,"Flowerbomb",
                "Viktor&Rolf",150.00,"IN_STOCK","Floral",LocalDate.parse("2018-05-09"));


        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7003/api/v1/products/" + productId)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(productResponseModel))
                );


        //DeliveryServiceClient
        String deliveryId="6eb13ffa-67fb-4bf7-aa44-a07050aa036a";
        String checkoutId="bb56bd39-0104-43f8-9a3e-624ec898d48f";
        DeliveryResponseModel deliveryResponseModel=new DeliveryResponseModel(deliveryId,checkoutId,"5th RainbowStreet",
                "IN_DELIVERY",productResponseModel,checkoutResponseModel,clientResponseModel,"4th streetAddress",
                "Montreal","Canada","Quebec","H4N 1G4","514-214-5324",LocalDate.parse("2023-04-01"),"CA");

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7003/api/v1/deliveries/" + deliveryId)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(deliveryResponseModel))
                );
    }


    //tests DiscountServiceClient
    @Test
    public void whenFieldIsValid_ThenReturnAllDiscounts() throws URISyntaxException, JsonProcessingException {
        Integer inventoriesReturned=1;


        DeliveryRequestModel deliveryRequestModel= DeliveryRequestModel.builder()
                .discountId("282b6579-2f3d-4758-b7b8-f6ddd838ad8e")
                .inventoryId("fd82210a-27c9-401f-bcba-9878e1a3d880")
                .checkoutId("bb56bd39-0104-43f8-9a3e-624ec898d48f")
                .productId("b3af9b91-db94-4e9e-ae63-aa382b442eb3")
                .postalCode("H4N 1G4")
                .country("Canada")
                .province("Quebec")
                .city("Montreal")
                .streetAddress("3rd Rainbow Road")
                .shippingUpdate(String.valueOf(ShippingUpdate.DELIVERED))
                .arrivalTime("2023-10-12")
                .phoneNumber("514-432-1232")
                .warehouseLocation("4th Ghost Road")
                .countryCode("CA")
                .build();


        SalePrices salePrices=new SalePrices(80.00);
        DiscountResponseModel discountResponseModel=new DiscountResponseModel(VALID_DISCOUNT_ID,20,SaleStatus.SALE,salePrices);


        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7002/api/v1/discounts" + VALID_DISCOUNT_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(discountResponseModel)));


        webTestClient.get()
                .uri(BASE_URI_DELIVERIES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(inventoriesReturned);


    }

    //tests inventoryServiceClient
    @Test
    public void whenFieldIsValid_ThenReturnAllInventories() throws URISyntaxException, JsonProcessingException {

        Integer inventoriesReturned=1;


        DeliveryRequestModel deliveryRequestModel= DeliveryRequestModel.builder()
                .discountId("282b6579-2f3d-4758-b7b8-f6ddd838ad8e")
                .inventoryId("fd82210a-27c9-401f-bcba-9878e1a3d880")
                .checkoutId("bb56bd39-0104-43f8-9a3e-624ec898d48f")
                .productId("b3af9b91-db94-4e9e-ae63-aa382b442eb3")
                .postalCode("H4N 1G4")
                .country("Canada")
                .province("Quebec")
                .city("Montreal")
                .streetAddress("3rd Rainbow Road")
                .shippingUpdate(String.valueOf(ShippingUpdate.DELIVERED))
                .arrivalTime("2023-10-12")
                .phoneNumber("514-432-1232")
                .warehouseLocation("4th Ghost Road")
                .countryCode("CA")
                .build();


        //required for get request in CLientSerivceClient
        String clientId="c3540a89-cb47-4c96-888e-ff96708db4d8";

        InventoryResponseModel inventoryResponseModel=new InventoryResponseModel(
                VALID_INVENTORY_ID,LocalDate.parse("2022-10-12"));


        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7002/api/v1/inventories" + VALID_INVENTORY_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(inventoryResponseModel)));


        webTestClient.get()
                .uri(BASE_URI_DELIVERIES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(inventoriesReturned);

    }

@Test
    public void whenUpdatedDeliveryWIthInvalidPostalValues_ThenReturnDelivery(){
        String expectedCheckoutId="bb56bd39-0104-43f8-9a3e-624ec898d48f";
        String expectedClientId="c3540a89-cb47-4c96-888e-ff96708db4d8";
        String expectedProductId="b3af9b91-db94-4e9e-ae63-aa382b442eb3";
        String expectedDiscountId="282b6579-2f3d-4758-b7b8-f6ddd838ad8e";
        String expectedInventoryId="fd82210a-27c9-401f-bcba-9878e1a3d880";

        String expectedWarehouseLocation = "34th street";
        String expectedPhoneNumber = "514-342-2235";
        LocalDate expectedArrivalTime = LocalDate.parse("2020-04-15");
        ShippingUpdate expectedShippingUpdate = ShippingUpdate.IN_DELIVERY;
        String expectedStreetAddress = "5th cherryStreet";
        String expectedCity = "New York";
        String expectedProvince = "None";
        String expectedCountry = "USA";
        String invalidPostalCode = "J5RRRRRRR 5J4";
        String expectedCountryCode = "US";

        DeliveryRequestModel deliveryRequestModel=newDeliveryRequestModel(expectedCheckoutId,expectedClientId,expectedProductId,expectedDiscountId,expectedInventoryId,expectedWarehouseLocation,expectedCity,expectedArrivalTime,expectedShippingUpdate,expectedPhoneNumber,expectedStreetAddress,expectedProvince,expectedCountry,invalidPostalCode,expectedCountryCode);

        webTestClient.put()
                .uri(BASE_URI_DELIVERIES +"/" + VALID_DELIVERY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(deliveryRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();
    }

    /*
@Test
    public void getAllDiscounts_ShouldBeSuccessful() throws URISyntaxException, JsonProcessingException {
    String clientId="c3540a89-cb47-4c96-888e-ff96708db4d8";

    //CLIENTSERVICECLIENT
    ClientResponseModel clientResponseModel=new ClientResponseModel(
            clientId,
            "Denisa","Hategan","denisahategan@gmail.com","135 Place Cote Vertu",
            "Montreal","Quebec","Canada","H4N 1G4","514-291-9304","QC");

    mockRestServiceServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:7002/api/v1/clients/" + clientId)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.writeValueAsString(clientResponseModel)));

//CHECKOUTSERVICESCLIENT
    CheckoutResponseModel checkoutResponseModel=new CheckoutResponseModel("bb56bd39-0104-43f8-9a3e-624ec898d48f",100.00,5.00,15.00,LocalDate.parse("2023-05-04"),120.00,
            "b3af9b91-db94-4e9e-ae63-aa382b442eb3","mastercard");

    mockRestServiceServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:7003/api/v1/checkouts/bb56bd39-0104-43f8-9a3e-624ec898d48f")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.writeValueAsString(checkoutResponseModel))
            );

    //InventoryServiceClient
    String inventoryId="fd82210a-27c9-401f-bcba-9878e1a3d880";
    InventoryResponseModel inventoryResponseModel=new InventoryResponseModel(inventoryId,LocalDate.parse("2022-06-05"));

    mockRestServiceServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:7003/api/v1/inventories/" + inventoryId)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.writeValueAsString(inventoryResponseModel))
            );


    //ProductServiceClient
    String productId="b3af9b91-db94-4e9e-ae63-aa382b442eb3";

      String discountId="282b6579-2f3d-4758-b7b8-f6ddd838ad8e";
    //  String inventoryId="fd82210a-27c9-401f-bcba-9878e1a3d880";
    ProductResponseModel productResponseModel=new ProductResponseModel(productId,discountId,inventoryId,"Flowerbomb",
            "Viktor&Rolf",150.00,"IN_STOCK","Floral",LocalDate.parse("2018-05-09"));


    mockRestServiceServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:7003/api/v1/products/" + productId + "/discounts")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.writeValueAsString(productResponseModel))
            );






//DISCOUNTSERVICECLIENT
    Integer expectedDiscountsReturned=1;
        SalePrices salePrices=new SalePrices(100.00);
        DiscountResponseModel discountResponseModel=new DiscountResponseModel(VALID_DISCOUNT_ID,50,SaleStatus.NOT_ON_SALE,salePrices);

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:7002/api/v1/discounts/")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(discountResponseModel)));

        String url=BASE_URI_DELIVERIES +"/" + VALID_DELIVERY_ID + "/clients/" + VALID_CLIENT_ID + "/checkouts/" + VALID_CHECKOUT_ID + "/products/" + VALID_PRODUCT_ID + "/discounts" ;
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.length()").isEqualTo(expectedDiscountsReturned);
    }






     */






    private DeliveryRequestModel newDeliveryRequestModel (String checkoutId,String clientId,String productId,String discountId,String inventoryId,String warehouseLocation, String phoneNumber, LocalDate arrivalTime, ShippingUpdate shippingUpdate,String streetAddress,String city,String province,String country,String postalCode,String countryCode){

        return DeliveryRequestModel.builder()

              .checkoutId(checkoutId)
                .productId(productId)
                .inventoryId(inventoryId)
                .discountId(discountId)
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