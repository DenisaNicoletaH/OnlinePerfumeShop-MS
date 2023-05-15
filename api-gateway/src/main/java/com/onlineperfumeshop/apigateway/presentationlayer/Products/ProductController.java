package com.onlineperfumeshop.apigateway.presentationlayer.Products;


import com.onlineperfumeshop.apigateway.businesslayer.Discount.DiscountService;
import com.onlineperfumeshop.apigateway.businesslayer.Inventory.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

        InventoryService inventoryService;

        DiscountService discountService;




        public ProductController(InventoryService inventoryService, DiscountService discountService) {
                this.inventoryService = inventoryService;
                this.discountService = discountService;
        }

        @GetMapping("/{productId}")
        public ProductResponseModel getProductId(@PathVariable String productId) {
                return inventoryService.getProductByIdentifier(productId);
        }


        @GetMapping()
        public ProductResponseModel[] getProducts() {
                return inventoryService.getProducts();
        }


        //products/discounts/discountId
        @GetMapping(
                value="/discount/{discountId}",
                produces="application/json")
        public ProductResponseModel[] getProductsByDiscountId(@PathVariable String discountId) {

                return inventoryService.getProductByDiscountId(discountId);
        }





        @DeleteMapping("/{productId}")
        public void deleteProducts(@PathVariable String productId){
                inventoryService.deleteProducts(productId);
        }


        @PutMapping("/{productId}")
        public void updateProduct(@RequestBody ProductRequestModel productRequestModel, @PathVariable String productId){
                 inventoryService.updateProduct(productRequestModel, productId);
        }



}