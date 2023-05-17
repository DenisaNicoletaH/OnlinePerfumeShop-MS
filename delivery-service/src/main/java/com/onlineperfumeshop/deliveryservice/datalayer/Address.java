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
public class Address {


    public String streetAddress;
    public String city;
    public String country;
    public String province;
    public String postalCode;


/*
    @SuppressWarnings("unused")
     public Address() {
    }

    public Address(@NotNull String streetAddress, @NotNull String city, @NotNull String province, @NotNull String country, @NotNull String postalCode) {

        Objects.requireNonNull(this.streetAddress = streetAddress);
        Objects.requireNonNull(this.city = city);
        Objects.requireNonNull(this.province = province);
        Objects.requireNonNull(this.country = country);
        Objects.requireNonNull(this.postalCode = postalCode);

    }


    public @NotNull String getStreetAddress() {
        return streetAddress;
    }

    public @NotNull String getCity() {
        return city;
    }

    public @NotNull String getProvince() {
        return province;
    }

    public @NotNull String getCountry() {
        return country;
    }

    public @NotNull String getPostalCode() {
        return postalCode;
    }

 */


}



