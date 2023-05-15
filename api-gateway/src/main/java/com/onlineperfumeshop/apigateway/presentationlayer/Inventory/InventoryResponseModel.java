package com.onlineperfumeshop.apigateway.presentationlayer.Inventory;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InventoryResponseModel {

    private String inventoryId;
    private LocalDate lastInventoryUpdate;
}
