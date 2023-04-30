package com.onlineperfumeshop.checkoutservice.presentationlayer;

import com.onlineperfumeshop.checkoutservice.businesslayer.CheckoutService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("api/v1/checkouts")
public class CheckoutController {

    CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }


    @PostMapping()
    public CheckoutResponseModel addCheckout(@RequestBody CheckoutRequestModel checkoutRequestModel){
        return checkoutService.addCheckout(checkoutRequestModel);


    }


    @GetMapping("/{checkoutId}")
    public CheckoutResponseModel getCheckoutId(@PathVariable String checkoutId) {
        return checkoutService.getCheckoutByIdentifier(checkoutId);
    }

    @GetMapping()
    public List<CheckoutResponseModel> getCheckouts() {
        return checkoutService.getCheckouts();
    }


    @PutMapping("/{checkoutId}")
    public CheckoutResponseModel updateCheckout(@RequestBody CheckoutRequestModel checkoutRequestModel, @PathVariable String checkoutId){
        return checkoutService.updateCheckout(checkoutRequestModel, checkoutId);
    }


    @DeleteMapping("/{checkoutId}")
    public void deleteCheckout(@PathVariable String checkoutId){
        checkoutService.deleteClient(checkoutId);
    }



}
