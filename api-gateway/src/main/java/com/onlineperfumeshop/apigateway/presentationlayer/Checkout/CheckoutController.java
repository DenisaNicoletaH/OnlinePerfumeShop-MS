package com.onlineperfumeshop.apigateway.presentationlayer.Checkout;

import com.onlineperfumeshop.apigateway.businesslayer.Checkout.CheckoutService;
import com.onlineperfumeshop.apigateway.domainclientlayer.CheckoutServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("api/v1/checkouts")
public class CheckoutController {

private CheckoutServiceClient checkoutServiceClient;

    public CheckoutController(CheckoutServiceClient checkoutServiceClient) {
        this.checkoutServiceClient = checkoutServiceClient;
    }

    @PostMapping()
    public ResponseEntity<CheckoutResponseModel> addCheckout(@RequestBody CheckoutRequestModel checkoutRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutServiceClient.addCheckout(checkoutRequestModel));


    }


    @GetMapping("/{checkoutId}")
    public CheckoutResponseModel getCheckoutId(@PathVariable String checkoutId) {
        return checkoutServiceClient.getCheckoutAggregate(checkoutId);
    }

    @GetMapping()
    public CheckoutResponseModel[] getCheckouts() {
        return checkoutServiceClient.getCheckouts();
    }


    @PutMapping("/{checkoutId}")
    public void updateCheckout(@RequestBody CheckoutRequestModel checkoutRequestModel, @PathVariable String checkoutId){
         checkoutServiceClient.updateCheckout(checkoutRequestModel, checkoutId);
    }


    @DeleteMapping("/{checkoutId}")
    public void deleteCheckout(@PathVariable String checkoutId){
        checkoutServiceClient.deleteCheckout(checkoutId);
    }



}
