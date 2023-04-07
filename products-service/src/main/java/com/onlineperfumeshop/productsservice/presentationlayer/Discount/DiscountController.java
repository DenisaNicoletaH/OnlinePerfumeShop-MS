package com.onlineperfumeshop.productsservice.presentationlayer.Discount;

import com.onlineperfumeshop.productsservice.businesslayer.Discount.DiscountService;
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
    public DiscountResponseModel addDiscount(@RequestBody DiscountRequestModel discountRequestModel) {
        return discountService.addDiscount(discountRequestModel);
    }

    @GetMapping("/{discountId}")
    public DiscountResponseModel getDiscountId(@PathVariable String discountId) {
        return discountService.getDiscountByIdentifier(discountId);
    }


    @DeleteMapping("/{discountId}")
    public void deleteDiscount(@PathVariable String discountId){
        discountService.deleteDiscount(discountId);
    }



}



