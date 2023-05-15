package com.onlineperfumeshop.apigateway.businesslayer.Delivery;


import com.onlineperfumeshop.apigateway.presentationlayer.Delivery.DeliveryRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Delivery.DeliveryResponseModel;

import java.util.List;

public interface DeliveryService{

    DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel);

    DeliveryResponseModel getDeliveryByIdentifier(String deliveryId);

    DeliveryResponseModel[] getDeliveries();

    void updateDelivery(DeliveryRequestModel deliveryRequestModel, String deliveryId);



}
