package com.onlineperfumeshop.productsservice.datalayer.Inventory;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "inventories")
@Data
public class Inventory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date lastInventoryUpdate;


    @Embedded
    private InventoryIdentifier inventoryIdentifier;

    public Inventory() {
        this.inventoryIdentifier = new InventoryIdentifier();
    }

    public Inventory(Date lastInventoryUpdate) {
        this.inventoryIdentifier = new InventoryIdentifier();
        this.lastInventoryUpdate = lastInventoryUpdate;
    }

}
