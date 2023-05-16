package com.onlineperfumeshop.deliveryservice.datalayer;

import java.util.UUID;

public class DeliveryIdentifier {

    private final String deliveryId;

    public DeliveryIdentifier() {
        this.deliveryId = UUID.randomUUID().toString();
    }


    public String getDeliveryId() {return this.deliveryId;}


}
