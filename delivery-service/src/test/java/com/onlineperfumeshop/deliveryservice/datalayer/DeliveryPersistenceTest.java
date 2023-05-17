package com.onlineperfumeshop.deliveryservice.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DeliveryPersistenceTest {
        private Delivery  presavedDelivery;
        @Autowired
        DeliveryRepository deliveryRepository;

        @BeforeEach
        public void setup(){
            deliveryRepository.deleteAll();
            CheckoutIdentifier checkoutIdentifier=new CheckoutIdentifier();
            DeliveryIdentifier deliveryIdentifier=new DeliveryIdentifier();
            String warehouseLocation ="22 Buena Vista Trail";
            ClientIdentifier clientIdentifier=new ClientIdentifier();
              LocalDate arrivalTime=LocalDate.parse("2023-06-21");
               ShippingUpdate shippingUpdate=ShippingUpdate.IN_DELIVERY;



            presavedDelivery = deliveryRepository.save(new Delivery(checkoutIdentifier,warehouseLocation,clientIdentifier,new Address(),new Phone(),arrivalTime,shippingUpdate));


        }
        //get the checkouts by id
        @Test
        public void findByDeliveryIdentifier_DeliveryId_ShouldSucceed() {


            //act
            Delivery found=deliveryRepository.findByDeliveryIdentifier_DeliveryId(presavedDelivery.getDeliveryIdentifier().getDeliveryId());

            //assert
            assertNotNull(found);
            assertThat(presavedDelivery,samePropertyValuesAs(found));
        }


        //IF checkout id is invalid, return null
        @Test
        public void findByDeliveryIdentifier_DeliveryId_ShouldReturnNull() {

            //act
            Delivery found = deliveryRepository.findByDeliveryIdentifier_DeliveryId(presavedDelivery.getDeliveryIdentifier().getDeliveryId() + 1);


            //assert
            assertNull(found);


        }


        @Test
        public void saveNewDelivery_shouldSuccess(){
            //arrange
            deliveryRepository.deleteAll();
            CheckoutIdentifier checkoutIdentifier=new CheckoutIdentifier();
            String warehouseLocation ="43 Street Trail";
            ClientIdentifier clientIdentifier=new ClientIdentifier();
            LocalDate arrivalTime=LocalDate.parse("2023-06-21");
            ShippingUpdate shippingUpdate=ShippingUpdate.IN_DELIVERY;


          Delivery  savedDelivery = deliveryRepository.save(new Delivery(checkoutIdentifier,warehouseLocation,clientIdentifier,new Address(),new Phone(),arrivalTime,shippingUpdate));

            //assert
            assertNotNull(savedDelivery);

            assertNotNull(savedDelivery.getId());

            assertNotNull(savedDelivery.getDeliveryIdentifier().getDeliveryId());
            assertEquals(checkoutIdentifier, savedDelivery.getCheckoutIdentifier());
            assertEquals(warehouseLocation, savedDelivery.getWarehouseLocation());
            assertEquals(arrivalTime, savedDelivery.getArrivalTime());
            assertEquals(shippingUpdate, savedDelivery.getShippingUpdate());
        }

        @Test
        public void deleteDeliveryWithValidIdentifier_ShouldSucceed(){
            deliveryRepository.delete(presavedDelivery);

            Delivery found = deliveryRepository.findByDeliveryIdentifier_DeliveryId(presavedDelivery.getDeliveryIdentifier().getDeliveryId());

            assertNull(found);
        }

        @Test
        public void updateDelivery_shouldSucceed(){
            //assert
            String updatedWarehouseLocation = "30 Warehouse Street Location";

            presavedDelivery.setWarehouseLocation(updatedWarehouseLocation);

            //act
            Delivery savedDelivery = deliveryRepository.save(presavedDelivery);

            //assert
            assertNotNull(savedDelivery);
            assertThat(savedDelivery, samePropertyValuesAs(presavedDelivery));
        }
    }



