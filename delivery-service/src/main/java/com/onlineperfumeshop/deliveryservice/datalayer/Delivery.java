package com.onlineperfumeshop.deliveryservice.datalayer;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "deliveries")
@Data
public class Delivery {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Embedded
    private CheckoutIdentifier checkoutIdentifier;

    @Embedded
    private DeliveryIdentifier deliveryIdentifier;

    private String warehouseLocation;

    @Embedded
    private ClientIdentifier clientIdentifier;
    @Embedded
    private Address address;

    @Embedded
    private Phone phone;


    private LocalDate arrivalTime;


  @Enumerated(EnumType.STRING)
    private ShippingUpdate shippingUpdate;


    public Delivery(CheckoutIdentifier checkoutIdentifier,String warehouseLocation, ClientIdentifier clientIdentifier, Address address, Phone phone, LocalDate arrivalTime, ShippingUpdate shippingUpdate) {
        this.checkoutIdentifier = checkoutIdentifier;
        this.deliveryIdentifier = new DeliveryIdentifier();
        this.warehouseLocation = warehouseLocation;
        this.clientIdentifier = clientIdentifier;
        this.address = address;
        this.phone = phone;
        this.arrivalTime = arrivalTime;
        this.shippingUpdate = shippingUpdate;
    }

    public Delivery() {
        this.deliveryIdentifier=new DeliveryIdentifier();
    }


    public @NotNull String getWarehouseLocation() {
        return warehouseLocation;
    }

    public  @NotNull ShippingUpdate getShippingUpdate() {
        return shippingUpdate;
    }
    public  @NotNull LocalDate getArrivalTime() {
        return arrivalTime;
    }
}
