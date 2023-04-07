package com.onlineperfumeshop.productsservice.datamapperlayer.Inventory;


import com.onlineperfumeshop.productsservice.datalayer.Inventory.Inventory;
import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel ="spring")
public interface InventoryResponseMapper {

    @Mapping(expression = "java(inventory.getInventoryIdentifier().getInventoryId())", target = "inventoryId")
    InventoryResponseModel entityToResponseModel(Inventory inventory);

    List<InventoryResponseModel> entityListToResponseModelList(List<Inventory> inventories);
}



