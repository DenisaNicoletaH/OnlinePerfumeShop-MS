package com.onlineperfumeshop.checkoutservice.businesslayer;


import com.onlineperfumeshop.checkoutservice.Utils.Exceptions.NegativeMoneyAmountException;
import com.onlineperfumeshop.checkoutservice.Utils.Exceptions.NotFoundException;
import com.onlineperfumeshop.checkoutservice.datalayer.Checkout;
import com.onlineperfumeshop.checkoutservice.datalayer.CheckoutRepository;
import com.onlineperfumeshop.checkoutservice.datalayer.PaymentMethod;
import com.onlineperfumeshop.checkoutservice.datalayer.ProductIdentifier;
import com.onlineperfumeshop.checkoutservice.datamapperlayer.CheckoutRequestMapper;
import com.onlineperfumeshop.checkoutservice.datamapperlayer.CheckoutResponseMapper;
import com.onlineperfumeshop.checkoutservice.presentationlayer.CheckoutRequestModel;
import com.onlineperfumeshop.checkoutservice.presentationlayer.CheckoutResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;

    private final CheckoutResponseMapper checkoutResponseMapper;
    private final CheckoutRequestMapper checkoutRequestMapper;

    public CheckoutServiceImpl(CheckoutRepository checkoutRepository, CheckoutResponseMapper checkoutResponseMapper, CheckoutRequestMapper checkoutRequestMapper) {
        this.checkoutRepository = checkoutRepository;
        this.checkoutResponseMapper = checkoutResponseMapper;
        this.checkoutRequestMapper = checkoutRequestMapper;
    }

    @Override
    public CheckoutResponseModel addCheckout(CheckoutRequestModel checkoutRequestModel) {

        if(checkoutRequestModel.getAmount() < 0)
            throw new NegativeMoneyAmountException("Amount is negative : " + checkoutRequestModel.getAmount());
        if(checkoutRequestModel.getTotalAmount() < 0)
            throw new NegativeMoneyAmountException("Total amount is negative : " + checkoutRequestModel.getTotalAmount());
        if(checkoutRequestModel.getShipping() < 0)
            throw new NegativeMoneyAmountException("Shipping cost is negative : " + checkoutRequestModel.getShipping());
        if(checkoutRequestModel.getTaxes() < 0)
            throw new NegativeMoneyAmountException("Taxes amount is negative : " + checkoutRequestModel.getTaxes());

        Checkout checkout = checkoutRequestMapper.entityToRequestModel(checkoutRequestModel);
        PaymentMethod paymentMethod = new PaymentMethod(checkoutRequestModel.getPaymentType(), checkoutRequestModel.getTotalAmount());
        checkout.setPaymentMethod(paymentMethod);
        checkout.setProductIdentifier(new ProductIdentifier(checkoutRequestModel.getProductId()));
        Checkout newCheckout = checkoutRepository.save(checkout);
        CheckoutResponseModel checkoutResponse = checkoutResponseMapper.entityToResponseModel(newCheckout);
        return checkoutResponse;
    }


    @Override
    public CheckoutResponseModel getCheckoutByIdentifier(String checkoutId) {
        Checkout checkout = checkoutRepository.findByCheckoutIdentifier_CheckoutId(checkoutId);
        if(checkout==null)
            throw new NotFoundException("No checkout was found with id : " + checkoutId);
        return checkoutResponseMapper.entityToResponseModel(checkout);

    }

    @Override
    public List<CheckoutResponseModel> getCheckouts() {
        return checkoutResponseMapper.entitiesToResponseModel(checkoutRepository.findAll());

    }

    @Override
    public void deleteCheckout(String checkoutId) {
        Checkout existingCheckout = checkoutRepository.findByCheckoutIdentifier_CheckoutId(checkoutId);

        if (existingCheckout == null) {
            throw new NotFoundException("No checkout was found with id : " + checkoutId);
        }

        checkoutRepository.delete(existingCheckout);

    }


    @Override
    public CheckoutResponseModel updateCheckout(CheckoutRequestModel checkoutRequestModel, String checkoutId) {
        if(checkoutRequestModel.getAmount() < 0)
            throw new NegativeMoneyAmountException("Amount is negative : " + checkoutRequestModel.getAmount());
        if(checkoutRequestModel.getTotalAmount() < 0)
            throw new NegativeMoneyAmountException("Total amount is negative : " + checkoutRequestModel.getTotalAmount());
        if(checkoutRequestModel.getShipping() < 0)
            throw new NegativeMoneyAmountException("Shipping cost is negative : " + checkoutRequestModel.getShipping());
        if(checkoutRequestModel.getTaxes() < 0)
            throw new NegativeMoneyAmountException("Taxes amount is negative : " + checkoutRequestModel.getTaxes());

        Checkout checkout = checkoutRequestMapper.entityToRequestModel(checkoutRequestModel);
        Checkout existingCheckout = checkoutRepository.findByCheckoutIdentifier_CheckoutId(checkoutId);
        if(existingCheckout == null){
            throw new NotFoundException("No checkout was found with id : " + checkoutId);
        }
        checkout.setId(existingCheckout.getId());
        checkout.setCheckoutIdentifier(existingCheckout.getCheckoutIdentifier());
        PaymentMethod paymentMethod = new PaymentMethod(checkoutRequestModel.getPaymentType(), checkoutRequestModel.getTotalAmount());
        checkout.setPaymentMethod(paymentMethod);

        checkout.setCheckoutIdentifier(existingCheckout.getCheckoutIdentifier());
        checkout.setId(existingCheckout.getId());

        Checkout updatedCheckout = checkoutRepository.save(checkout);

        CheckoutResponseModel checkoutResponseModel = checkoutResponseMapper.entityToResponseModel(updatedCheckout);
        return checkoutResponseModel;

    }

    }
