package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import net.minidev.json.JSONUtil;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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

    private HttpEntity<Transfer> createTransferRequest(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer,headers);
        return entity;
    }

//    private ResponseEntity<Map> sendTransferRequest(HttpEntity<Transfer> entity) {
//        try{
//            return restTemplate.exchange(baseUrl + "transfers", HttpMethod.POST, entity, Map.class);
//        }catch(RestClientResponseException ex){
//
//        }
//
//    }


    private HttpHeaders authHeaders(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return headers;
    }

}
