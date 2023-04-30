package com.onlineperfumeshop.clientsservice.presentationlayer;

import com.onlineperfumeshop.clientsservice.datalayer.Client;
import com.onlineperfumeshop.clientsservice.datalayer.ClientRepository;
import jdk.jshell.Snippet;
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
public class ClientControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ClientRepository clientRepository;


    private final String BASE_URI_CLIENTS = "/api/v1/clients";
    private final String VALID_CLIENTS_ID = "c3540a89-cb47-4c96-888e-ff96708db4d8";

    private final String VALID_CLIENTS_EMAIL = "aucceli0@dot.gov"; //<--- and here

    private final String VALID_CLIENTS_FIRSTNAME = "Alick"; //<--- and here
    private final String VALID_CLIENTS_LASTNAME = "Ucceli"; //<--- and here

    private final String VALID_CLIENTS_COUNTRY = "Canada"; //<--- and here
    private final String VALID_CLIENTS_PROVINCE = "British-Colombia"; //<--- and here
    private final String VALID_CLIENTS_STREETADDRESS = "73 Shoshone Road"; //<--- and here
    private final String VALID_CLIENTS_POSTALCODE = "P0M 2T6"; //<--- and here
    private final String VALID_CLIENTS_PHONENUMBER = "994-836-0622"; //<--- and here
    private final String VALID_CLIENTS_COUNTRYCODE = "BC"; //<--- and here
    private final String VALID_CLIENTS_CITY = "Vancouver"; //<--- and here


    //gets all clients--> based on sql
    @Sql({"/data-mysql.sql"})
    @Test
    public void whenClientsExists_thenReturnClient() {

        Integer clientReturned = 10;

        webTestClient.get()
                .uri(BASE_URI_CLIENTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.length()").isEqualTo(clientReturned);

    }


    @Test
    public void whenClientIdExists_thenReturnClient() {

        webTestClient.get()
                .uri(BASE_URI_CLIENTS + "/" + VALID_CLIENTS_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.clientId").isEqualTo(VALID_CLIENTS_ID)
                .jsonPath("$.emailAddress").isEqualTo(VALID_CLIENTS_EMAIL);
    }


    @Test
    public void whenCreatedClientWithValidValues_ThenReturnNewClient() {
        String expectedFName = "Denisa";
        String expectedLName = "Hategan";
        String expectedEmail = "denisa@gmail.com";
        String expectedStreetAddress = "35 Flower Road";
        String expectedCity = "Montreal";
        String expectedProvince = "Quebec";
        String expectedPostalCode = "J4T 5J8";
        String expectedCountry = "Canada";
        String expectedPhone = "514-221-0070";
        String expectedCountryCode = "QC";

        ClientRequestModel clientRequestModel = newClientRequestModel(expectedFName, expectedLName, expectedEmail, expectedCity, expectedCountry, expectedStreetAddress, expectedProvince, expectedPostalCode, expectedPhone, expectedCountryCode);


        webTestClient.post()
                .uri(BASE_URI_CLIENTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ClientResponseModel.class)
                .value(val -> {
                    assertNotNull(val);
                    assertEquals(val.getFirstName(), expectedFName);
                    assertEquals(val.getLastName(), expectedLName);
                    assertEquals(val.getEmailAddress(), expectedEmail);
                    assertEquals(val.getCity(), expectedCity);
                    assertEquals(val.getCountry(), expectedCountry);
                    assertEquals(val.getStreetAddress(), expectedStreetAddress);
                    assertEquals(val.getProvince(), expectedProvince);
                    assertEquals(val.getPostalCode(), expectedPostalCode);
                    assertEquals(val.getPhoneNumber(), expectedPhone);
                    assertEquals(val.getCountryCode(), expectedCountryCode);


                });

    }

    @Test
    public void whenUpdatedClientWithValidValues_thenReturnUpdatedClient() {

        ClientRequestModel clientRequestModel = newClientRequestModel(VALID_CLIENTS_FIRSTNAME, VALID_CLIENTS_LASTNAME, VALID_CLIENTS_EMAIL, VALID_CLIENTS_CITY, VALID_CLIENTS_COUNTRY, VALID_CLIENTS_STREETADDRESS, VALID_CLIENTS_PROVINCE, VALID_CLIENTS_POSTALCODE, VALID_CLIENTS_PHONENUMBER, VALID_CLIENTS_COUNTRYCODE);

        webTestClient.put()
                .uri(BASE_URI_CLIENTS + "/" + VALID_CLIENTS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.clientId").isEqualTo(VALID_CLIENTS_ID)
                .jsonPath("$.firstName").isEqualTo(VALID_CLIENTS_FIRSTNAME)
                .jsonPath("$.lastName").isEqualTo(VALID_CLIENTS_LASTNAME)
                .jsonPath("$.emailAddress").isEqualTo(VALID_CLIENTS_EMAIL)
                .jsonPath("$.city").isEqualTo(VALID_CLIENTS_CITY)
                .jsonPath("$.country").isEqualTo(VALID_CLIENTS_COUNTRY)
                .jsonPath("$.streetAddress").isEqualTo(VALID_CLIENTS_STREETADDRESS)
                .jsonPath("$.province").isEqualTo(VALID_CLIENTS_PROVINCE)
                .jsonPath("$.postalCode").isEqualTo(VALID_CLIENTS_POSTALCODE)
                .jsonPath("$.phoneNumber").isEqualTo(VALID_CLIENTS_PHONENUMBER)
                .jsonPath("$.countryCode").isEqualTo(VALID_CLIENTS_COUNTRYCODE);


    }


    //Delete
    @Test

    public void whenDeletedExistingClient_thenDeleteClient() {
        //arrange
//    ClientRequestModel clientRequestModel = newClientRequestModel(VALID_CLIENTS_FIRSTNAME, VALID_CLIENTS_LASTNAME, VALID_CLIENTS_EMAIL, VALID_CLIENTS_CITY,VALID_CLIENTS_COUNTRY,VALID_CLIENTS_STREETADDRESS,VALID_CLIENTS_PROVINCE,VALID_CLIENTS_POSTALCODE, VALID_CLIENTS_PHONENUMBER, VALID_CLIENTS_COUNTRYCODE);



        //act
        webTestClient.delete()
                .uri(BASE_URI_CLIENTS + "/" + VALID_CLIENTS_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();

        Client found = clientRepository.findByClientIdentifier_ClientId(VALID_CLIENTS_ID);

        assertNull(found);
    }

@Test
    public void whenDeletedNotExistingClient_thenReturnNotFound() {
        //arrange
//    ClientRequestModel clientRequestModel = newClientRequestModel(VALID_CLIENTS_FIRSTNAME, VALID_CLIENTS_LASTNAME, VALID_CLIENTS_EMAIL, VALID_CLIENTS_CITY,VALID_CLIENTS_COUNTRY,VALID_CLIENTS_STREETADDRESS,VALID_CLIENTS_PROVINCE,VALID_CLIENTS_POSTALCODE, VALID_CLIENTS_PHONENUMBER, VALID_CLIENTS_COUNTRYCODE);


        //act
        webTestClient.delete()
                .uri(BASE_URI_CLIENTS + "/" + "invalid_id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound().expectBody().jsonPath("$").isNotEmpty();


    }


    private ClientRequestModel newClientRequestModel(String firstName, String lastName, String emailAddress, String city, String country, String streetAddress, String province, String postalCode, String phoneNumber, String countryCode) {

        return ClientRequestModel.builder()

                .firstName(firstName)
                .lastName(lastName)
                .emailAddress(emailAddress)
                .country(country)
                .province(province)
                .city(city)
                .postalCode(postalCode)
                .streetAddress(streetAddress)
                .phoneNumber(phoneNumber)
                .countryCode(countryCode).build();

    }




    //Add Client--> phone regex and Postalcode exception
    @Test
    public void whenAddedClientWithPhoneValidValues_ThenReturnNewClient() {
        String invalidPostalCode = "J5R-J5C";
        String expectedPostalCode = "H4N 1G4";

        String invalidPhoneNumber = "+1 514-2khbu13-4322";
        String validPhoneNumber = "514-343-3223";

        String expectedFName = "Denisa";
        String expectedLName = "Hategan";
        String expectedEmail = "denisa@gmail.com";
        String expectedStreetAddress = "35 Flower Road";
        String expectedCity = "Montreal";
        String expectedProvince = "Quebec";
        String expectedCountry = "Canada";
        String expectedCountryCode = "QC";

        ClientRequestModel clientRequestModel = newClientRequestModel(expectedFName, expectedLName, expectedEmail, expectedCity, expectedCountry, expectedStreetAddress, expectedProvince, expectedPostalCode, invalidPhoneNumber, expectedCountryCode);


        webTestClient.post()
                .uri(BASE_URI_CLIENTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();


    }

    @Test
    public void whenAddedClientWithPostalValidValues_ThenReturnNewClient() {

        String invalidPostalCode = "J5R-J5C";
        String expectedPostalCode = "H4N 1G4";

        String invalidPhoneNumber = "+1 514-2khbu13-4322";
        String validPhoneNumber = "514-343-3223";

        String expectedFName = "Denisa";
        String expectedLName = "Hategan";
        String expectedEmail = "denisa@gmail.com";
        String expectedStreetAddress = "35 Flower Road";
        String expectedCity = "Montreal";
        String expectedProvince = "Quebec";
        String expectedCountry = "Canada";
        String expectedCountryCode = "QC";

        ClientRequestModel clientRequestModel = newClientRequestModel(expectedFName, expectedLName, expectedEmail, expectedCity, expectedCountry, expectedStreetAddress, expectedProvince, invalidPostalCode, validPhoneNumber, expectedCountryCode);


        webTestClient.post()
                .uri(BASE_URI_CLIENTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();



    }

    //PUT ---> regex PostalCode and Phone

    @Test
    public void whenUpdatedClientWithPhoneValidValues_ThenReturnClient() {
        String invalidPostalCode = "J5R-J5C";
        String expectedPostalCode = "H4N 1G4";

        String invalidPhoneNumber = "+1 514-2khbu13-4322";
        String validPhoneNumber = "514-343-3223";

        String expectedFName = "Denisa";
        String expectedLName = "Hategan";
        String expectedEmail = "denisa@gmail.com";
        String expectedStreetAddress = "35 Flower Road";
        String expectedCity = "Montreal";
        String expectedProvince = "Quebec";
        String expectedCountry = "Canada";
        String expectedCountryCode = "QC";

        ClientRequestModel clientRequestModel = newClientRequestModel(expectedFName, expectedLName, expectedEmail, expectedCity, expectedCountry, expectedStreetAddress, expectedProvince, expectedPostalCode, invalidPhoneNumber, expectedCountryCode);


        webTestClient.put()
                .uri(BASE_URI_CLIENTS + "/" + VALID_CLIENTS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();


    }

    @Test
    public void whenUpdatedClientWithInvalidId_thenReturnNotFound() {
        String expectedPostalCode = "H4N 1G4";

        String validPhoneNumber = "514-343-3223";

        String expectedFName = "Denisa";
        String expectedLName = "Hategan";
        String expectedEmail = "denisa@gmail.com";
        String expectedStreetAddress = "35 Flower Road";
        String expectedCity = "Montreal";
        String expectedProvince = "Quebec";
        String expectedCountry = "Canada";
        String expectedCountryCode = "QC";

        ClientRequestModel clientRequestModel = newClientRequestModel(expectedFName, expectedLName, expectedEmail, expectedCity, expectedCountry, expectedStreetAddress, expectedProvince, expectedPostalCode, validPhoneNumber, expectedCountryCode);


        webTestClient.put()
                .uri(BASE_URI_CLIENTS + "/" + "invalid_id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();


    }
    @Test
    public void whenUpdatedClientWithPostalValidValues_ThenReturnClient() {
        String invalidPostalCode = "J5R-J5C";

        String validPhoneNumber = "514-343-3223";

        String expectedFName = "Denisa";
        String expectedLName = "Hategan";
        String expectedEmail = "denisa@gmail.com";
        String expectedStreetAddress = "35 Flower Road";
        String expectedCity = "Montreal";
        String expectedProvince = "Quebec";
        String expectedCountry = "Canada";
        String expectedCountryCode = "QC";

        ClientRequestModel clientRequestModel = newClientRequestModel(expectedFName, expectedLName, expectedEmail, expectedCity, expectedCountry, expectedStreetAddress, expectedProvince, invalidPostalCode, validPhoneNumber, expectedCountryCode);


        webTestClient.put()
                .uri(BASE_URI_CLIENTS + "/" + VALID_CLIENTS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$").isNotEmpty();


    }

    //ADD-->CONFLICT EXCEPTION ADD

    @Test
    public void whenAddedClientWithValidValues_FirstName_ThenReturnClient() {





        ClientRequestModel clientRequestModel = newClientRequestModel(VALID_CLIENTS_FIRSTNAME, VALID_CLIENTS_LASTNAME, VALID_CLIENTS_EMAIL, VALID_CLIENTS_CITY, VALID_CLIENTS_COUNTRY, VALID_CLIENTS_STREETADDRESS, VALID_CLIENTS_PROVINCE, VALID_CLIENTS_POSTALCODE, VALID_CLIENTS_PHONENUMBER, VALID_CLIENTS_COUNTRYCODE);

        webTestClient.post()
                .uri(BASE_URI_CLIENTS )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(clientRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isNotEmpty();



    }



    }



