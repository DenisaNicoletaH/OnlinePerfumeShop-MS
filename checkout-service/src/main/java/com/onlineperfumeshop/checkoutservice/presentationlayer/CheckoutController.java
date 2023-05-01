package com.onlineperfumeshop.checkoutservice.presentationlayer;

import com.onlineperfumeshop.checkoutservice.businesslayer.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CheckoutResponseModel> addCheckout(@RequestBody CheckoutRequestModel checkoutRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.addCheckout(checkoutRequestModel));


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
        checkoutService.deleteCheckout(checkoutId);
    }



}
