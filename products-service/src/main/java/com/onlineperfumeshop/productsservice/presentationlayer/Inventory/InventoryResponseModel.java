package com.onlineperfumeshop.productsservice.presentationlayer.Inventory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryResponseModel {

    private String inventoryId;
    private LocalDate lastInventoryUpdate;
}
