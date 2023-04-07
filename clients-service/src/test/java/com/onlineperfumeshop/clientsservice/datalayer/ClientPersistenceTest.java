package com.onlineperfumeshop.clientsservice.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClientPersistenceTest {

    private Client presavedClient;
    @Autowired
    ClientRepository clientRepository;

    @BeforeEach
    public void setup(){
        clientRepository.deleteAll();
        String expectedFName = "Radu";
        String expectedLName = "Hategan";
        String expectedEmail = "radu@gmail.com";
        String expectedStreetAddress = "55 Stone Road";
        String expectedCity = "Montreal";
        String expectedProvince = "Quebec";
        String expectedPostalCode = "J6P 2J4";
        String expectedCountry = "Canada";
        String expectedPhone = "514-278-9010";
        String expectedCountryCode = "QC";

        presavedClient = clientRepository.save(new Client(expectedFName,expectedLName,expectedEmail,expectedStreetAddress,expectedCity,expectedProvince,expectedPostalCode, expectedCountry,expectedPhone,expectedCountryCode));
    }
    //get the clients by id
    @Test
    public void findByClientIdentifier_ClientId_ShouldSucceed() {


        //act
        Client found=clientRepository.findByClientIdentifier_ClientId(presavedClient.getClientIdentifier().getClientId());

        //assert
        assertNotNull(found);
        assertThat(presavedClient,samePropertyValuesAs(found));
    }


    //IF client id is invalid, return null
    @Test
    public void findByClientIdentifier_ClientId_ShouldReturnNull() {

        //act
        Client found = clientRepository.findByClientIdentifier_ClientId(presavedClient.getClientIdentifier().getClientId() + 1);


        //assert
        assertNull(found);


    }


    @Test
    public void saveNewClient_shouldSuccess(){
        //arrange
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

        Client savedClient = clientRepository.save(new Client(expectedFName,expectedLName,expectedEmail,expectedStreetAddress,expectedCity,expectedProvince,expectedPostalCode, expectedCountry,expectedPhone,expectedCountryCode));

        //assert
        assertNotNull(savedClient);

        assertNotNull(savedClient.getId());

        assertNotNull(savedClient.getClientIdentifier().getClientId());

        assertEquals(expectedFName, savedClient.getFirstName());
        assertEquals(expectedLName, savedClient.getLastName());
        assertEquals(expectedCity, savedClient.getAddress().getCity());
        assertEquals(expectedCountry, savedClient.getAddress().getCountry());
        assertEquals(expectedEmail, savedClient.getEmailAddress());
        assertEquals(expectedCountryCode, savedClient.getPhone().getCountryCode());
        assertEquals(expectedPostalCode, savedClient.getAddress().getPostalCode());
        assertEquals(expectedPhone, savedClient.getPhone().getPhoneNumber());
        assertEquals(expectedProvince, savedClient.getAddress().getProvince());
        assertEquals(expectedStreetAddress, savedClient.getAddress().getStreetAddress());

    }

    @Test
    public void deleteClientWithValidIdentifier_ShouldSucceed(){
        clientRepository.delete(presavedClient);

        Client found = clientRepository.findByClientIdentifier_ClientId(presavedClient.getClientIdentifier().getClientId());

        assertNull(found);
    }

    @Test
    public void updateClient_shouldSucceed(){
        //assert
        String updatedName = "Carmen";

        presavedClient.setFirstName(updatedName);

        //act
        Client savedClient = clientRepository.save(presavedClient);

        //assert
        assertNotNull(savedClient);
        assertThat(savedClient, samePropertyValuesAs(presavedClient));
    }
}