package com.onlineperfumeshop.productsservice.datamapperlayer.Inventory;


import com.onlineperfumeshop.productsservice.datalayer.Inventory.Inventory;
import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inventoryIdentifier", ignore = true)
    @Mapping(expression = "java(requestModel.getLastInventoryUpdated())",target = "lastInventoryUpdate")
    Inventory requestModelToEntity(InventoryRequestModel requestModel);
}
