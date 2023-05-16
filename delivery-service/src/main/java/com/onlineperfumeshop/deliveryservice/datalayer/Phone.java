package com.onlineperfumeshop.deliveryservice.datalayer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Phone {


    public String phoneNumber;
    public String countryCode;

    /*
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

     */


}
