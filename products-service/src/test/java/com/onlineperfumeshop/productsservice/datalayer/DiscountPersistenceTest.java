package com.onlineperfumeshop.productsservice.datalayer;


import com.onlineperfumeshop.productsservice.datalayer.Discount.Discount;
import com.onlineperfumeshop.productsservice.datalayer.Discount.DiscountRepository;
import com.onlineperfumeshop.productsservice.datalayer.Discount.SaleStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static com.onlineperfumeshop.productsservice.datalayer.Discount.SaleStatus.NOT_ON_SALE;
import static com.onlineperfumeshop.productsservice.datalayer.Discount.SaleStatus.SALE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class DiscountPersistenceTest {

    private Discount presavedDiscount;

    @Autowired
    DiscountRepository discountRepository;

    @BeforeEach
    public void setup(){

        discountRepository.deleteAll();
        Integer expectedSalePercent=50;
        Double expectedNewPrices=100.00;
        SaleStatus expectedSaleStatus = SALE;
   presavedDiscount=discountRepository.save(new Discount(expectedSalePercent,expectedNewPrices, expectedSaleStatus));

    }

    //get discountbyId
    @Test
    public void findByDiscountIdentifier_DiscountId_ShouldSucceed(){

        //act
        Discount found =discountRepository.findByDiscountIdentifier_DiscountId(presavedDiscount.getDiscountIdentifier().getDiscountId());
        assertThat(presavedDiscount,samePropertyValuesAs(found));
    }

    // return null if no id

    @Test
    public void findByDiscountIdentifier_DiscountId_ShouldReturnNull(){

        //act
        Discount found=discountRepository.findByDiscountIdentifier_DiscountId(presavedDiscount.getDiscountIdentifier().getDiscountId() +1);

        //assert
        assertNull(found);

    }

    @Test
    public void saveNewDiscount_ShouldSucceed(){
        //arrange

        Integer expectedSalePercent=50;
        Double expectedNewPrices=100.00;
        SaleStatus expectedSaleStatus = SALE;

        Discount savedDiscount=discountRepository.save(new Discount(expectedSalePercent,expectedNewPrices,expectedSaleStatus));

        //assert
        assertNotNull(savedDiscount);
        assertNotNull(savedDiscount.getId());
        assertNotNull(savedDiscount.getDiscountIdentifier().getDiscountId());
        assertEquals(expectedSalePercent, savedDiscount.getSalePercent());
        assertEquals(expectedNewPrices, savedDiscount.getSalePrices());
        assertEquals(expectedSaleStatus,savedDiscount.getSaleStatus());

    }

    @Test
    public void deleteDiscountWithValidIdentifier_ShouldSucceed(){

        discountRepository.delete(presavedDiscount);

        Discount found=discountRepository.findByDiscountIdentifier_DiscountId(presavedDiscount.getDiscountIdentifier().getDiscountId());

        assertNull(found);
    }

    @Test
    public void updateDiscount_ShouldSucceed(){
        //assert
        Integer updatedSalePercent=100;
        SaleStatus updatedSaleStatus = NOT_ON_SALE;



        presavedDiscount.setSalePercent(updatedSalePercent);
        presavedDiscount.setSaleStatus(updatedSaleStatus);

        //act
        Discount savedDiscount=discountRepository.save(presavedDiscount);

        //assert
        assertNotNull(savedDiscount);
        assertThat(savedDiscount,samePropertyValuesAs(presavedDiscount));
    }

}
