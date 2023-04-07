package com.onlineperfumeshop.checkoutservice.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CheckoutPersistanceTest {
    private Checkout presavedCheckout;
    @Autowired
    CheckoutRepository checkoutRepository;

    @BeforeEach
    public void setup(){
        checkoutRepository.deleteAll();
        Double amount =  54.43;
        Double taxes = 7.96;
        Double shipping = 15.99;
        Double total = 100.00;
        LocalDate endOfSale = LocalDate.parse("2022-07-30");
        String paymentMethod = "mastercard";


        presavedCheckout = checkoutRepository.save(new Checkout(amount,taxes,shipping,endOfSale,paymentMethod,total));


    }
    //get the checkouts by id
    @Test
    public void findByCheckoutIdentifier_CheckoutId_ShouldSucceed() {


        //act
        Checkout found=checkoutRepository.findByCheckoutIdentifier_CheckoutId(presavedCheckout.getCheckoutIdentifier().getCheckoutId());

        //assert
        assertNotNull(found);
        assertThat(presavedCheckout,samePropertyValuesAs(found));
    }


    //IF checkout id is invalid, return null
    @Test
    public void findByCheckoutIdentifier_CheckoutId_ShouldReturnNull() {

        //act
        Checkout found = checkoutRepository.findByCheckoutIdentifier_CheckoutId(presavedCheckout.getCheckoutIdentifier().getCheckoutId() + 1);


        //assert
        assertNull(found);


    }

//add
    @Test
    public void saveNewCheckout_shouldSuccess(){
        //arrange
        checkoutRepository.deleteAll();
        Double amount =  100.30;
        Double taxes = 5.00;
        Double shipping = 20.00;
        Double total = 125.00;
        LocalDate endOfSale = LocalDate.parse("2020-02-24");
        String paymentMethod = "mastercard";

        Checkout savedCheckout = checkoutRepository.save(new Checkout(amount,taxes,shipping,endOfSale,paymentMethod,total));

        //assert
        assertNotNull(savedCheckout);

        assertNotNull(savedCheckout.getId());

        assertNotNull(savedCheckout.getCheckoutIdentifier().getCheckoutId());
        assertEquals(amount, savedCheckout.getAmount());
        assertEquals(taxes, savedCheckout.getTaxes());
        assertEquals(shipping, savedCheckout.getShipping());
        assertEquals(endOfSale, savedCheckout.getEndOfSaleDate());
        assertEquals(total, savedCheckout.getPaymentMethod().getTotalAmount());


    }

    @Test
    public void deleteCheckoutWithValidIdentifier_ShouldSucceed(){
        checkoutRepository.delete(presavedCheckout);

        Checkout found = checkoutRepository.findByCheckoutIdentifier_CheckoutId(presavedCheckout.getCheckoutIdentifier().getCheckoutId());

        assertNull(found);
    }

    @Test
    public void updateCheckout_shouldSucceed(){
        //assert
        Double updatedAmount = 200.50;

        presavedCheckout.setAmount(updatedAmount);

        //act
        Checkout savedCheckout = checkoutRepository.save(presavedCheckout);

        //assert
        assertNotNull(savedCheckout);
        assertThat(savedCheckout, samePropertyValuesAs(presavedCheckout));
    }
}

