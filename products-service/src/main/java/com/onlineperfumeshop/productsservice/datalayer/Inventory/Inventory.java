package com.onlineperfumeshop.productsservice.datalayer.Inventory;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "inventories")
@Data
public class Inventory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate lastInventoryUpdate;


    @Embedded
    private InventoryIdentifier inventoryIdentifier;

    public Inventory() {
        this.inventoryIdentifier = new InventoryIdentifier();
    }

    public Inventory(LocalDate lastInventoryUpdate) {
        this.inventoryIdentifier = new InventoryIdentifier();
        this.lastInventoryUpdate = lastInventoryUpdate;
    }

}
