package com.onlineperfumeshop.deliveryservice.Utils;

import com.onlineperfumeshop.deliveryservice.datalayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoaderService implements CommandLineRunner {

@Autowired
    DeliveryRepository deliveryRepository;



@Override
    public void run(String...args) throws Exception {
    DeliveryIdentifier deliveryIdentifier = new DeliveryIdentifier("6eb13ffa-67fb-4bf7-aa44-a07050aa036a");
    ClientIdentifier clientIdentifier = new ClientIdentifier("c3540a89-cb47-4c96-888e-ff96708db4d8");
    CheckoutIdentifier checkoutIdentifier = new CheckoutIdentifier("bb56bd39-0104-43f8-9a3e-624ec898d48f");


    List<DeliveryIdentifier> delivery = new ArrayList<>();
    List<ClientIdentifier> clients = new ArrayList<>();

//
    delivery.add(deliveryIdentifier);
    clients.add(clientIdentifier);


    Address address = Address.builder()
            .streetAddress("3th Street")
            .postalCode("H4N 1G4")
            .city("Montreal")
            .country("Canada")
            .province("Quebec").build();



    Phone phone= Phone.builder()
            .phoneNumber("514-231-1421")
                    .countryCode("ON").build();



    Delivery delivery1 = Delivery.builder()
            .clientIdentifier(clientIdentifier)
            .deliveryIdentifier(deliveryIdentifier)
            .checkoutIdentifier(checkoutIdentifier)
            .clientFirstName("Denisa")
            .clientLastName("Hategan")
            .address(address)
            .phone(phone)
            .shippingUpdate(ShippingUpdate.IN_DELIVERY)
            .warehouseLocation("Cloud Road")
            .arrivalTime(LocalDate.parse("2022-10-12")).build();

    deliveryRepository.insert(delivery1);


}


}
