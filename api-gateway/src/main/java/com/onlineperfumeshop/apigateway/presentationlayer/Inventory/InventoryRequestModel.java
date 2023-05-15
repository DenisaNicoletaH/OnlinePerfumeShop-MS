package com.onlineperfumeshop.apigateway.presentationlayer.Inventory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryRequestModel {

   private LocalDate lastInventoryUpdated;


}

