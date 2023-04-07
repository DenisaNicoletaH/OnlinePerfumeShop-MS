package com.onlineperfumeshop.checkoutservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository  extends JpaRepository<Checkout, Integer> {

    Checkout findByCheckoutIdentifier_CheckoutId(String checkoutId);

}

