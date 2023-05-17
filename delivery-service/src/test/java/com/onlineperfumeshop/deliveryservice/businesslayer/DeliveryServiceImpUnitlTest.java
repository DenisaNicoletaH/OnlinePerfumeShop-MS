package com.onlineperfumeshop.deliveryservice.businesslayer;

import com.onlineperfumeshop.deliveryservice.datalayer.CheckoutIdentifier;
import com.onlineperfumeshop.deliveryservice.datalayer.ClientIdentifier;
import com.onlineperfumeshop.deliveryservice.datalayer.DeliveryIdentifier;
import com.onlineperfumeshop.deliveryservice.datamapperlayer.DeliveryRequestMapper;
import com.onlineperfumeshop.deliveryservice.datamapperlayer.DeliveryResponseMapper;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Checkout.CheckoutServiceClient;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Clients.ClientServiceClient;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Products.DiscountServiceClient;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Products.InventoryServiceClient;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Products.ProductServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration")
class DeliveryServiceImpUnitlTest {


    @Autowired
    DeliveryService deliveryService;

    @MockBean
    ClientServiceClient clientServiceClient;

    @MockBean
    ProductServiceClient productServiceClient;

    @MockBean
    DiscountServiceClient discountServiceClient;

    @MockBean
    InventoryServiceClient inventoryServiceClient;

    @MockBean
    CheckoutServiceClient checkoutServiceClient;



    @SpyBean
    DeliveryResponseMapper deliveryResponseMapper;

    DeliveryRequestMapper deliveryRequestMapper;





    @Test
    void addDelivery_ShouldSucceed() {
    }

    @Test
    void getDeliveryByIdentifier() {
    }

    @Test
    void getDeliveries() {
    }

    @Test
    void updateDelivery() {
    }
}