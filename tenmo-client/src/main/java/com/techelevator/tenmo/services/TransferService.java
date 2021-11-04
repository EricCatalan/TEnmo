package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private String baseUrl = "http://localhost:8080/";
    RestTemplate restTemplate = new RestTemplate();

    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl + "transfers/";
    }

    public Transfer createTransfer(Transfer transfer, String authToken) {
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, authHeaders(authToken));
        ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Transfer.class);
        return response.getBody();
    }

    private HttpHeaders authHeaders(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return headers;
    }

}
