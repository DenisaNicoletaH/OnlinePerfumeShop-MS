package com.onlineperfumeshop.deliveryservice.domainclientlayer.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InventoryResponseModel {

    private String inventoryId;
    private LocalDate lastInventoryUpdate;
}
