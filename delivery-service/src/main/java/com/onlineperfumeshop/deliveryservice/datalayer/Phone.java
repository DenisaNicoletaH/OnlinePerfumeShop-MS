package com.onlineperfumeshop.deliveryservice.datalayer;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Embeddable

public class Phone {


    public String phoneNumber;
    public String countryCode;

public Phone(@NotNull String phoneNumber, @NotNull String countryCode){
    Objects.requireNonNull(this.phoneNumber = phoneNumber);
    Objects.requireNonNull(this.countryCode = countryCode);
}
@SuppressWarnings("unused")
    public Phone() {

    }

    public @NotNull String getPhoneNumber(){return phoneNumber;}

public @NotNull String getCountryCode() {
    return countryCode;
}


}
