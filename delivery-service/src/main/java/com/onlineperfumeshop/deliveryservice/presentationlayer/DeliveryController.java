package com.onlineperfumeshop.deliveryservice.presentationlayer;

import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.DeliveryInvalidInputException;
import com.onlineperfumeshop.deliveryservice.businesslayer.DeliveryService;
import com.onlineperfumeshop.deliveryservice.datalayer.DeliveryIdentifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/deliveries")
public class DeliveryController {

    private final String deliveryPostalCode = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
    private final String deliveryPhoneNumber = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";

    DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }


    @PostMapping()
    public ResponseEntity<DeliveryResponseModel> addDelivery(@RequestBody DeliveryRequestModel deliveryRequestModel){
        if (!Pattern.compile(deliveryPostalCode).matcher(deliveryRequestModel.getPostalCode()).matches()) {
            throw new DeliveryInvalidInputException("Invalid postal code" + deliveryRequestModel.getPostalCode());
        } else if (!Pattern.compile(deliveryPhoneNumber).matcher(deliveryRequestModel.getPhoneNumber()).matches()) {
            throw new DeliveryInvalidInputException("The phone number is invalid" + deliveryRequestModel.getPhoneNumber());
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(deliveryService.addDelivery(deliveryRequestModel));
        }
    }




    @GetMapping("/{deliveryId}")
    public DeliveryResponseModel getDeliveryId(@PathVariable String deliveryId) {

        return deliveryService.getDeliveryByIdentifier(deliveryId);
    }

    @GetMapping()
    public List<DeliveryResponseModel> getDeliveries() {
        return deliveryService.getDeliveries();
    }


    @PutMapping("/{deliveryId}")
    public DeliveryResponseModel updateDelivery(@RequestBody DeliveryRequestModel deliveryRequestModel, @PathVariable String deliveryId){
        if (!Pattern.compile(deliveryPostalCode).matcher(deliveryRequestModel.getPostalCode()).matches()) {
            throw new DeliveryInvalidInputException("Invalid postal code" + deliveryRequestModel.getPostalCode());
        } else if (!Pattern.compile(deliveryPhoneNumber).matcher(deliveryRequestModel.getPhoneNumber()).matches()) {
            throw new DeliveryInvalidInputException("The phone number is invalid " + deliveryRequestModel.getPhoneNumber());
        }else {
            return deliveryService.updateDelivery(deliveryRequestModel, deliveryId);
        }

    }

}
