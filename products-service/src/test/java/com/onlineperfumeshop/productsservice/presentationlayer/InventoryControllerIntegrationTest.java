package com.onlineperfumeshop.productsservice.presentationlayer;

import com.onlineperfumeshop.productsservice.datalayer.Inventory.Inventory;
import com.onlineperfumeshop.productsservice.datalayer.Inventory.InventoryRepository;
import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
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
public class InventoryControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    InventoryRepository inventoryRepository;

    private final String BASE_URI_INVENTORIES = "/api/v1/inventories";

    private final String VALID_INVENTORY_ID = "13b581aa-1fcf-4d80-b8ab-68a9c791a299";

    private final LocalDate VALID_LAST_UPDATE_DATE = LocalDate.parse("2018-11-19");

    @Sql({"/data-mysql.sql"})
    @Test
    public void whenInventoryExists_ThenReturnDelivery() {

        Integer inventoryReturned = 10;

        webTestClient.get()
                .uri(BASE_URI_INVENTORIES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(inventoryReturned);

    }

    @Test
    public void whenInventoryIdExists_ThenReturnInventory() {
        webTestClient.get()
                .uri(BASE_URI_INVENTORIES + "/" + VALID_INVENTORY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.inventoryId").isEqualTo(VALID_INVENTORY_ID);

    }

    @Test
    public void whenCreatedInventoryWithValidValues_ThenReturnNewInventory() {

        String expectedInventoryId = "13b581aa-1fcf-4d80-b8ab-68a9c791a299";
        LocalDate expectedLastUpdated = LocalDate.parse("2018-11-19");

        InventoryRequestModel inventoryRequestModel = newInventoryRequestModel(expectedLastUpdated);


        webTestClient.post()
                .uri(BASE_URI_INVENTORIES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(InventoryResponseModel.class)
                .value(val -> {
                    assertNotNull(val);
                    assertEquals(val.getLastInventoryUpdate(), expectedLastUpdated);
                });

    }





    private InventoryRequestModel newInventoryRequestModel(LocalDate lastUpdated) {

        return InventoryRequestModel.builder()
                .lastInventoryUpdated(lastUpdated).build();
    }

@Test
public void whenDeletedNotExistingCheckout_ThenReturnNotFound() {

    webTestClient.delete()
            .uri(BASE_URI_INVENTORIES + "/" + "invalid_id")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound().expectBody().jsonPath("$").isNotEmpty();


}
}
