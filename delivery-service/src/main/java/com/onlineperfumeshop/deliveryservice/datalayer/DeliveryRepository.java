package com.onlineperfumeshop.deliveryservice.datalayer;


import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {
    Delivery findByDeliveryIdentifier_DeliveryId(String deliveryId);

    Boolean existsDeliveriesByAddress_StreetAddressAndAddress_PostalCodeAndAddress_CityAndAddress_Country(String streetAddress,String postalCode,String city, String country );

    Boolean findDeliveriesById(String deliveryId);
}
