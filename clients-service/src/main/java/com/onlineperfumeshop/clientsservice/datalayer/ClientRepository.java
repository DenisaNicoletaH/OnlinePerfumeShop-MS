package com.onlineperfumeshop.clientsservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

    public interface ClientRepository extends JpaRepository<Client, Integer> {

        Client findByClientIdentifier_ClientId(String clientId);

        Boolean existsByFirstNameAndLastName(String firstName, String lastName);

        Boolean existsClientByEmailAddress(String emailAddress);

        Boolean existsClientsByFirstNameAndLastNameAndEmailAddress(String firstName,String lastName,String emailAddress);
    }
