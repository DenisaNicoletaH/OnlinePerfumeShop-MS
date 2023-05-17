package com.onlineperfumeshop.apigateway.businesslayer.Delivery;

import com.onlineperfumeshop.apigateway.domainclientlayer.CheckoutServiceClient;
import com.onlineperfumeshop.apigateway.domainclientlayer.ClientServiceClient;
import com.onlineperfumeshop.apigateway.domainclientlayer.DeliveryServiceClient;
import com.onlineperfumeshop.apigateway.presentationlayer.Delivery.DeliveryRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Delivery.DeliveryResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {


    private final DeliveryServiceClient deliveryServiceClient;

    private final CheckoutServiceClient checkoutServiceClient;
    private final ClientServiceClient clientServiceClient;


    public DeliveryServiceImpl(DeliveryServiceClient deliveryServiceClient, CheckoutServiceClient checkoutServiceClient, ClientServiceClient clientServiceClient){
        this.deliveryServiceClient=deliveryServiceClient;
        this.checkoutServiceClient=checkoutServiceClient;
        this.clientServiceClient=clientServiceClient;
    }
    @Override
    public DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel) {
        return deliveryServiceClient.addDelivery(deliveryRequestModel);
    }

    @Override
    public DeliveryResponseModel getDeliveryByIdentifier(String deliveryId) {
        return deliveryServiceClient.getDeliveryAggregate(deliveryId);

    }

    @Override
    public DeliveryResponseModel[] getDeliveries() {
        return deliveryServiceClient.getDeliveries();
    }

    @Override
    public void updateDelivery(DeliveryRequestModel deliveryRequestModel, String deliveryId) {
         deliveryServiceClient.updateDeliveries(deliveryRequestModel, deliveryId);
    }


}
