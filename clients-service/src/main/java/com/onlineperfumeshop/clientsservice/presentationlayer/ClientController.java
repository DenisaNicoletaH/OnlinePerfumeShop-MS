package com.onlineperfumeshop.clientsservice.presentationlayer;


import Utils.Exceptions.ClientInvalidInputException;
import com.onlineperfumeshop.clientsservice.businesslayer.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final String postalCode = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
    private final String phoneNumber = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$";

    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ClientResponseModel addClient(@RequestBody ClientRequestModel clientRequestModel) {
        if (!Pattern.compile(postalCode).matcher(clientRequestModel.getPostalCode()).matches()) {
    throw new ClientInvalidInputException("Invalid postal code" + clientRequestModel.getPostalCode());
        } else if (!Pattern.compile(phoneNumber).matcher(clientRequestModel.getPhoneNumber()).matches()) {
            throw new ClientInvalidInputException("The phone number is invalid" + clientRequestModel.getPhoneNumber());
        }else {
            return clientService.addClient(clientRequestModel);
        }
    }
    @GetMapping("/{clientId}")
    public ClientResponseModel getClientId(@PathVariable String clientId) {
        return clientService.getClientByIdentifier(clientId);
    }

    @GetMapping()
    public List<ClientResponseModel> getClients() {
        return clientService.getClients();
    }


    @PutMapping("/{clientId}")
    public ClientResponseModel updateClient(@RequestBody ClientRequestModel clientRequestModel, @PathVariable String clientId){
        if (!Pattern.compile(postalCode).matcher(clientRequestModel.getPostalCode()).matches()) {
            throw new ClientInvalidInputException("Invalid postal code" + clientRequestModel.getPostalCode());
        } else if (!Pattern.compile(phoneNumber).matcher(clientRequestModel.getPhoneNumber()).matches()) {
            throw new ClientInvalidInputException("The phone number is invalid " + clientRequestModel.getPhoneNumber());
        }else {
            return clientService.updateClient(clientRequestModel, clientId);
        }
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable String clientId){
        clientService.deleteClient(clientId);
    }



}
