package com.onlineperfumeshop.deliveryservice.datalayer;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.time.LocalDate;
import java.util.Date;

@Document(collection = "deliveries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {


    // Id OR id
    @Id
    private String id;

    private CheckoutIdentifier checkoutIdentifier;

    private DeliveryIdentifier deliveryIdentifier;
    private ClientIdentifier clientIdentifier;


    private String clientFirstName;

    private String clientLastName;





    private String warehouseLocation;

    private Address address;

    private Phone phone;


    private LocalDate arrivalTime;


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
