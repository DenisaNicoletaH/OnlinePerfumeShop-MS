package com.onlineperfumeshop.productsservice.presentationlayer.Discount;

import com.onlineperfumeshop.productsservice.businesslayer.Discount.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/discounts")
public class DiscountController {
   DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }


    @GetMapping()
    public List<DiscountResponseModel> getDiscounts() {
        return discountService.getDiscounts();
    }


    @PostMapping()
    ResponseEntity <DiscountResponseModel> addDiscount(@RequestBody DiscountRequestModel discountRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(discountService.addDiscount(discountRequestModel));
    }

    @GetMapping("/{discountId}")
    ResponseEntity <DiscountResponseModel> getDiscountId(@PathVariable String discountId) {
        return ResponseEntity.status(HttpStatus.OK).body(discountService.getDiscountByIdentifier(discountId));
    }


    @DeleteMapping("/{discountId}")
    public ResponseEntity <Void> deleteDiscount(@PathVariable String discountId){
        discountService.deleteDiscount(discountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}



