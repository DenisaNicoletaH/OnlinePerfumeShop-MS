package com.onlineperfumeshop.deliveryservice.datalayer;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {
    Delivery findByDeliveryIdentifier_DeliveryId(String deliveryId);

    Boolean existsDeliveriesByAddress_StreetAddressAndAddress_PostalCodeAndAddress_CityAndAddress_Country(String streetAddress,String postalCode,String city, String country );

}
