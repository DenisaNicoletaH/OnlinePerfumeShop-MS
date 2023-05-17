package com.onlineperfumeshop.deliveryservice.domainclientlayer.Clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientResponseModel {

        private String clientId;

        private String firstName;

        private String lastName;

        private String emailAddress;

        private String streetAddress;

        private String city;

        private String province;

        private String country;

        private String postalCode;

        private String phoneNumber;

        private String countryCode;
}
