package com.onlineperfumeshop.apigateway.presentationlayer.Inventory;

import com.onlineperfumeshop.apigateway.businesslayer.Inventory.InventoryService;
import com.onlineperfumeshop.apigateway.businesslayer.Inventory.InventoryServiceImpl;
import com.onlineperfumeshop.apigateway.presentationlayer.Products.ProductRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Products.ProductResponseModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventories")
public class InventoryController {
    InventoryService inventoryService;


    public InventoryController(InventoryServiceImpl inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping()
    public ResponseEntity <InventoryResponseModel[]> getInventories() {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getInventories());
    }

    @PostMapping()
    ResponseEntity<InventoryResponseModel> addInventory(@RequestBody InventoryRequestModel inventoryRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addInventory(inventoryRequestModel));
    }

    @GetMapping("/{inventoryId}")
    ResponseEntity<InventoryResponseModel> getInventoryByInventoryIdentifier(@PathVariable String inventoryId){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getInventoryByInventoryIdentifier(inventoryId));
    }


    @PutMapping("/{inventoryId}/products")
     ResponseEntity<Void> updateInventory(@RequestBody InventoryRequestModel inventoryRequestModel, @PathVariable String productId) {
        inventoryService.updateInventory(inventoryRequestModel, productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

@PostMapping("/{inventoryId}/products")
ResponseEntity<ProductResponseModel> addProductToInventory(@RequestBody ProductRequestModel productRequestModel,
                                                           @PathVariable String inventoryId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addProductToInventory(productRequestModel, inventoryId));
    }

    @GetMapping("/{inventoryId}/products")
   ResponseEntity<ProductResponseModel[]> getProductsByInventoryIdentifier_InventoryId(@PathVariable String inventoryId){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getProductsByInventoryIdentifier_InventoryId(inventoryId));
    }


}
