package com.onlineperfumeshop.checkoutservice.presentationlayer;

import com.onlineperfumeshop.checkoutservice.datalayer.Checkout;
import com.onlineperfumeshop.checkoutservice.datalayer.CheckoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CheckoutControllerIntegrationTest {

}
