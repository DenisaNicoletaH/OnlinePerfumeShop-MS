package com.onlineperfumeshop.clientsservice.datalayer;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;


    @Embedded
    private ClientIdentifier clientIdentifier;

    private String firstName;

    private String lastName;

    private String emailAddress;

    @Embedded
    private Address address;

    @Embedded
    private Phone phone;

    public Client() {
        this.clientIdentifier = new ClientIdentifier();
    }

    public Client(String firstName, String lastName, String emailAddress, String streetAddress, String city, String province, String postalCode, String country, String phoneNumber, String countryCode) {

        this.clientIdentifier = new ClientIdentifier();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = new Address(streetAddress, city, province, country, postalCode);
        this.phone = new Phone(phoneNumber, countryCode);
    }


    public @NotNull String getFirstName() {
        return firstName;
    }

    public @NotNull String getLastName() {
        return lastName;
    }
    public @NotNull String getEmailAddress() {
        return emailAddress;
    }

}
