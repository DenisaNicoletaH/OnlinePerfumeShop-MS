package com.onlineperfumeshop.deliveryservice.businesslayer;

import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.ConflictDeliveryException;
import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.DeliveryNotFoundException;
import com.onlineperfumeshop.deliveryservice.datalayer.*;
import com.onlineperfumeshop.deliveryservice.datamapperlayer.DeliveryRequestMapper;
import com.onlineperfumeshop.deliveryservice.datamapperlayer.DeliveryResponseMapper;
import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryRequestModel;
import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService{


    private DeliveryRepository deliveryRepository;
    private DeliveryResponseMapper deliveryResponseMapper;
    private DeliveryRequestMapper deliveryRequestMapper;


    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, DeliveryResponseMapper deliveryResponseMapper, DeliveryRequestMapper deliveryRequestMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryResponseMapper = deliveryResponseMapper;
        this.deliveryRequestMapper = deliveryRequestMapper;
    }
    @Override
    public DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel, DeliveryIdentifier deliveryIdentifier) {
        Delivery delivery = deliveryRequestMapper.entityToRequestModel(deliveryRequestModel);

        delivery.setClientIdentifier(new ClientIdentifier(deliveryRequestModel.getClientId()));
        delivery.setCheckoutIdentifier(new CheckoutIdentifier(deliveryRequestModel.getCheckoutId()));
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        DeliveryResponseModel deliveryResponseModel = deliveryResponseMapper.entityToResponseModel(updatedDelivery);
        return deliveryResponseModel;

    }




    @Override
    public DeliveryResponseModel getDeliveryByIdentifier(String deliveryId) {
        return deliveryResponseMapper.entityToResponseModel(deliveryRepository.findByDeliveryIdentifier_DeliveryId(deliveryId));

    }

    @Override
    public List<DeliveryResponseModel> getDeliveries() {
        return deliveryResponseMapper.entitiesToResponseModel(deliveryRepository.findAll());

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

            DeliveryResponseModel deliveryResponse = deliveryResponseMapper.entityToResponseModel(newDelivery);
            return deliveryResponse;
        }
    }

}
