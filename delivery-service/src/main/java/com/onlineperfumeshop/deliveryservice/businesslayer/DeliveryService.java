package com.onlineperfumeshop.deliveryservice.businesslayer;

import com.onlineperfumeshop.deliveryservice.datalayer.DeliveryIdentifier;
import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryRequestModel;
import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryResponseModel;

import java.util.List;

public interface DeliveryService {


    DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel, DeliveryIdentifier deliveryIdentifier);

    DeliveryResponseModel getDeliveryByIdentifier(String deliveryId);

    List<DeliveryResponseModel> getDeliveries();

    DeliveryResponseModel updateDelivery(DeliveryRequestModel deliveryRequestModel, String deliveryId);


}
