package com.onlineperfumeshop.productsservice.datalayer.Inventory;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class InventoryIdentifier {

    private String inventoryId;

    public InventoryIdentifier() {
        this.inventoryId = UUID.randomUUID().toString();
    }

    public InventoryIdentifier(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryId() {
        return inventoryId;
    }
}
