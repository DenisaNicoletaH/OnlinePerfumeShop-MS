package com.onlineperfumeshop.productsservice.presentationlayer.Inventory;

import com.onlineperfumeshop.productsservice.businesslayer.Inventory.InventoryServiceImpl;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductResponseModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventories")
public class InventoryController {
    InventoryServiceImpl inventoryService;


    public InventoryController(InventoryServiceImpl inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping()
    public List<InventoryResponseModel> getInventories() {
        return inventoryService.getInventories();
    }

    @PostMapping()
    public InventoryResponseModel addInventory(@RequestBody InventoryRequestModel inventoryRequestModel) {
        return inventoryService.addInventory(inventoryRequestModel);
    }

    @GetMapping("/{inventoryId}")
    InventoryResponseModel getInventoryByInventoryIdentifier(@PathVariable String inventoryId){
        return inventoryService.getInventoryByInventoryIdentifier(inventoryId);
    }


    @PutMapping("/{inventoryId}/products")
    public InventoryResponseModel updateInventory(@RequestBody InventoryRequestModel inventoryRequestModel, @RequestParam String productId) {
        return inventoryService.updateInventory(inventoryRequestModel, productId);
    }


    @DeleteMapping("/{inventoryId}")
    public void deleteInventory(@PathVariable String inventoryId) {
        inventoryService.deleteInventory(inventoryId);
    }

@PostMapping("/{inventoryId}/products")
ProductResponseModel addProductToInventory(@RequestBody ProductRequestModel productRequestModel,
                                           @PathVariable String inventoryId) {
        return inventoryService.addProductToInventory(productRequestModel, inventoryId);
    }

    @GetMapping("/{inventoryId}/products")
   List<ProductResponseModel> getProductsByInventoryIdentifier_InventoryId(@PathVariable String inventoryId){
        return inventoryService.getProductsByInventoryIdentifier_InventoryId(inventoryId);
    }


}
