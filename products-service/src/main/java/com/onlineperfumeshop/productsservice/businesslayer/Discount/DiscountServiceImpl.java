package com.onlineperfumeshop.productsservice.businesslayer.Discount;


import Utils.Exceptions.NotFoundException;
import com.onlineperfumeshop.productsservice.datalayer.Discount.Discount;
import com.onlineperfumeshop.productsservice.datalayer.Discount.DiscountRepository;
import com.onlineperfumeshop.productsservice.datalayer.Discount.SalePrices;
import com.onlineperfumeshop.productsservice.datamapperlayer.Discount.DiscountRequestMapper;
import com.onlineperfumeshop.productsservice.datamapperlayer.Discount.DiscountResponseMapper;
import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {


    private DiscountRepository discountRepository;

    private DiscountResponseMapper discountResponseMapper;
    private DiscountRequestMapper discountRequestMapper;


    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountResponseMapper discountResponseMapper, DiscountRequestMapper discountRequestMapper) {
        this.discountRepository = discountRepository;
        this.discountResponseMapper = discountResponseMapper;
        this.discountRequestMapper = discountRequestMapper;
    }

    @Override
    public DiscountResponseModel addDiscount(DiscountRequestModel discountRequestModel) {
        Discount discount=discountRequestMapper.requestModelToEntity(discountRequestModel);
        SalePrices salePrices=new SalePrices(discountRequestModel.getSalePrices().getNewPrices());
        discount.setSalePrices(salePrices);

        Discount newDiscount=discountRepository.save(discount);
        DiscountResponseModel discountResponse = discountResponseMapper.entityToResponseModel(newDiscount);
        return discountResponse;
    }

    @Override
    public DiscountResponseModel getDiscountByIdentifier(String discountId) {
        Discount discount = discountRepository.findByDiscountIdentifier_DiscountId(discountId);
        if (discount == null) {
            throw new NotFoundException("No discount was found with id : " + discountId);
        }

        return discountResponseMapper.entityToResponseModel(discount);
    }

    @Override
    public List<DiscountResponseModel> getDiscounts() {
        return discountResponseMapper.entityListToResponseModelList(discountRepository.findAll());


    }

    @Override
    public void deleteDiscount(String discountId) {
            Discount existingDiscount = discountRepository.findByDiscountIdentifier_DiscountId(discountId);

            if (existingDiscount == null) {
                throw new NotFoundException("No discount was found with id : " + discountId);
            }

            discountRepository.delete(existingDiscount);

        }
    }


