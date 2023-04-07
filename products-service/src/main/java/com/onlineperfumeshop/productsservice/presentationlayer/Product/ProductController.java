package com.onlineperfumeshop.productsservice.presentationlayer.Product;

import com.onlineperfumeshop.productsservice.businesslayer.Discount.DiscountService;
import com.onlineperfumeshop.productsservice.businesslayer.Inventory.InventoryService;
import com.onlineperfumeshop.productsservice.datamapperlayer.Product.ProductDiscountResponseMapper;
import com.onlineperfumeshop.productsservice.presentationlayer.ProductDiscountResponseModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

        InventoryService inventoryService;

        DiscountService discountService;



        ProductDiscountResponseMapper productDiscountResponseMapper;

        public ProductController(InventoryService inventoryService, DiscountService discountService, ProductDiscountResponseMapper productDiscountResponseMapper) {
                this.inventoryService = inventoryService;
                this.discountService = discountService;
                this.productDiscountResponseMapper = productDiscountResponseMapper;
        }

        @GetMapping("/{productId}")
        public ProductResponseModel getProductId(@PathVariable String productId) {
                return inventoryService.getProductByIdentifier(productId);
        }


        @GetMapping()
        public List<ProductResponseModel> getProducts() {
                return inventoryService.getProducts();
        }

        @GetMapping("/discounts/{discountId}")
        public ProductDiscountResponseModel getProductsByDiscountId(@PathVariable String discountId) {

                return inventoryService.getProductByDiscountId(discountId);
        }





        @DeleteMapping("/{productId}")
        public void deleteProducts(@PathVariable String productId){
                inventoryService.deleteProducts(productId);
        }


        @PutMapping("/{productId}")
        public ProductResponseModel updateProduct(@RequestBody ProductRequestModel productRequestModel, @RequestParam String productId){
                return inventoryService.updateProduct(productRequestModel, productId);
        }

}