package com.onlineperfumeshop.clientsservice.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class ClientIdentifier {


        private String clientId;

     public ClientIdentifier() {
            this.clientId = UUID.randomUUID().toString();
        }

    //public ClientIdentifier(String clientId) {
     //   this.clientId = clientId;
    //}

    public String getClientId() {return this.clientId;}



}
