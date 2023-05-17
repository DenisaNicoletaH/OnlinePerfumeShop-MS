package com.onlineperfumeshop.deliveryservice.businesslayer;

import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.ConflictDeliveryException;
import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.DeliveryNotFoundException;
import com.onlineperfumeshop.deliveryservice.datalayer.*;
import com.onlineperfumeshop.deliveryservice.datamapperlayer.DeliveryRequestMapper;
import com.onlineperfumeshop.deliveryservice.datamapperlayer.DeliveryResponseMapper;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Checkout.CheckoutResponseModel;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Checkout.CheckoutServiceClient;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Clients.ClientResponseModel;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Clients.ClientServiceClient;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Products.*;
import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryRequestModel;
import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryResponseModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final ClientServiceClient clientServiceClient;
    private final ProductServiceClient productServiceClient;

    private final InventoryServiceClient inventoryServiceClient;

    private final DiscountServiceClient discountServiceClient;

    private final CheckoutServiceClient checkoutServiceClient;


    private final DeliveryRepository deliveryRepository;
    private final DeliveryResponseMapper deliveryResponseMapper;
    private final DeliveryRequestMapper deliveryRequestMapper;


    public DeliveryServiceImpl(ClientServiceClient clientServiceClient, ProductServiceClient productServiceClient, InventoryServiceClient inventoryServiceClient, DiscountServiceClient discountServiceClient, CheckoutServiceClient checkoutServiceClient, DeliveryRepository deliveryRepository, DeliveryResponseMapper deliveryResponseMapper, DeliveryRequestMapper deliveryRequestMapper) {
        this.clientServiceClient = clientServiceClient;
        this.productServiceClient = productServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
        this.discountServiceClient = discountServiceClient;
        this.checkoutServiceClient = checkoutServiceClient;
        this.deliveryRepository = deliveryRepository;
        this.deliveryResponseMapper = deliveryResponseMapper;
        this.deliveryRequestMapper = deliveryRequestMapper;
    }



    @Override
    public DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel) {
       // Delivery delivery = deliveryRequestMapper.entityToRequestModel(deliveryRequestModel);

        ClientResponseModel clientResponseModel = clientServiceClient.getClientAggregate(deliveryRequestModel.getClientId());
        CheckoutResponseModel checkoutResponseModel = checkoutServiceClient.getCheckoutAggregate(deliveryRequestModel.getCheckoutId());



        ProductResponseModel productResponseModel = productServiceClient.getProductAggregate(checkoutResponseModel.getProductId());


        /*
        delivery.setClientIdentifier(new ClientIdentifier(clientResponseModel.getClientId()));
        delivery.setCheckoutIdentifier(new CheckoutIdentifier(deliveryRequestModel.getCheckoutId()));
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        DeliveryResponseModel deliveryResponseModel = deliveryResponseMapper.entityToResponseModel(updatedDelivery);
                 */

        Address address=Address.builder()
                .streetAddress(deliveryRequestModel.getStreetAddress())
                .city(deliveryRequestModel.getCity())
                .country(deliveryRequestModel.getCountry())
                .province(deliveryRequestModel.getProvince())
                .postalCode(deliveryRequestModel.getPostalCode()).build();

        Phone phone=Phone.builder()
                .phoneNumber(deliveryRequestModel.getPhoneNumber())
                .countryCode(deliveryRequestModel.getCountryCode()).build();

        Delivery delivery=Delivery.builder()
                .deliveryIdentifier(new DeliveryIdentifier())
                .checkoutIdentifier(new CheckoutIdentifier(checkoutResponseModel.getCheckoutId()))
                .clientIdentifier(new ClientIdentifier(deliveryRequestModel.getClientId()))
                .clientFirstName(clientResponseModel.getFirstName())
                .clientLastName(clientResponseModel.getLastName())
                .address(address)
                .phone(phone)
                .build();

        delivery.setShippingUpdate(ShippingUpdate.valueOf(deliveryRequestModel.getShippingUpdate()));
        delivery.setArrivalTime(LocalDate.parse(deliveryRequestModel.getArrivalTime()));
        delivery.setWarehouseLocation(deliveryRequestModel.getWarehouseLocation());


        Delivery savedDelivery= deliveryRepository.save(delivery);

//        ShippingUpdate shippingUpdateStatus=ShippingUpdate.IN_DELIVERY;

//        switch(savedDelivery.getShippingUpdate()) {
//            case IN_DELIVERY -> shippingUpdateStatus = ShippingUpdate.IN_DELIVERY;
//            case IN_WAREHOUSE -> shippingUpdateStatus = ShippingUpdate.IN_WAREHOUSE;
//            case DELIVERED -> shippingUpdateStatus = ShippingUpdate.DELIVERED;
//
//        }
//



    //checkoutServiceClient.updateCheckout(checkoutRequestModel,checkoutRequestModel.getProductId());

        savedDelivery.setClientFirstName(clientResponseModel.getFirstName());
        savedDelivery.setClientLastName(clientResponseModel.getLastName());
    return deliveryResponseMapper.entityToResponseModel(savedDelivery, productResponseModel,checkoutResponseModel,clientResponseModel);
    }


    @Override
    public DeliveryResponseModel getDeliveryByIdentifier(String deliveryId) {

     ClientResponseModel clientResponseModel= clientServiceClient.getClientAggregate(deliveryId);
        Delivery delivery = deliveryRepository.findByDeliveryIdentifier_DeliveryId(deliveryId);
        CheckoutResponseModel checkoutResponseModel = checkoutServiceClient.getCheckoutAggregate(delivery.getCheckoutIdentifier().getCheckoutId());



        ProductResponseModel productResponseModel = productServiceClient.getProductAggregate(checkoutResponseModel.getProductId());

        return deliveryResponseMapper.entityToResponseModel(delivery,productResponseModel, checkoutResponseModel,clientResponseModel);
    }

    @Override
    public List<DeliveryResponseModel> getDeliveries() {
        List<Delivery> deliveries=deliveryRepository.findAll();
        return deliveryResponseMapper.entitiesToResponseModel(deliveries);

    }

    @Override
    public DeliveryResponseModel updateDelivery(DeliveryRequestModel deliveryRequestModel, String deliveryId) {
        Delivery delivery = deliveryRequestMapper.entityToRequestModel(deliveryRequestModel);
        Delivery existingDelivery = deliveryRepository.findByDeliveryIdentifier_DeliveryId(deliveryId);
        if (existingDelivery == null) {
            throw new DeliveryNotFoundException("No delivery was found with id  : " + deliveryId);
        }
        delivery.setId(existingDelivery.getId());
        delivery.setDeliveryIdentifier(existingDelivery.getDeliveryIdentifier());
        delivery.setClientIdentifier(new ClientIdentifier(deliveryRequestModel.getClientId()));
        delivery.setCheckoutIdentifier(new CheckoutIdentifier(deliveryRequestModel.getCheckoutId()));
        delivery.setAddress(new Address(deliveryRequestModel.getStreetAddress(), deliveryRequestModel.getCity(), deliveryRequestModel.getProvince(), deliveryRequestModel.getCountry(), deliveryRequestModel.getPostalCode()));

        delivery.setDeliveryIdentifier(existingDelivery.getDeliveryIdentifier());
        delivery.setId(existingDelivery.getId());

        Boolean existing = deliveryRepository.existsDeliveriesByAddress_StreetAddressAndAddress_PostalCodeAndAddress_CityAndAddress_Country(deliveryRequestModel.getStreetAddress(), deliveryRequestModel.getPostalCode(), deliveryRequestModel.getCity(), deliveryRequestModel.getCountry());
        if (existing) {
            throw new ConflictDeliveryException("This is invalid" + (deliveryRequestModel.getStreetAddress() + " " + deliveryRequestModel.getPostalCode() + " " + deliveryRequestModel.getCity() + " " + deliveryRequestModel.getCountry()));
        } else {
            Delivery newDelivery = deliveryRepository.save(delivery);
            CheckoutResponseModel checkoutResponseModel = checkoutServiceClient.getCheckoutAggregate(delivery.getCheckoutIdentifier().getCheckoutId());



            ProductResponseModel productResponseModel = productServiceClient.getProductAggregate(checkoutResponseModel.getProductId());
            DeliveryResponseModel deliveryResponse = deliveryResponseMapper.entityToResponseModel(newDelivery,productResponseModel,checkoutResponseModel,clientServiceClient.getClientAggregate(deliveryRequestModel.getClientId()));
            return deliveryResponse;
        }
    }

}
