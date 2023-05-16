package com.onlineperfumeshop.deliveryservice.businesslayer;

import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryRequestModel;
import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryResponseModel;

import java.util.List;

public interface DeliveryService {


    DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel);

   //DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel);

    DeliveryResponseModel getDeliveryByIdentifier(String deliveryId);

    List<DeliveryResponseModel> getDeliveries();

    DeliveryResponseModel updateDelivery(DeliveryRequestModel deliveryRequestModel, String deliveryId);


}
