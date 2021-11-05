package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import net.minidev.json.JSONUtil;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TransferService {

    private String baseUrl = "http://localhost:8080/";
    RestTemplate restTemplate = new RestTemplate();

    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void createTransfer(Transfer transfer, String authToken) {
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, authHeaders(authToken));
        ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfers", HttpMethod.POST, entity, Transfer.class);
    }

    public List<Transfer> listUserTransfers(String authToken) {
        HttpEntity<Transfer> entity = new HttpEntity<>(authHeaders(authToken));
        Transfer[] allTransfers = null;
        try {
            allTransfers = restTemplate.exchange(baseUrl + "mytransfers", HttpMethod.GET, entity, Transfer[].class).getBody();
        } catch(NullPointerException npe) {
            System.out.println("You have no transfer history");
        }

        return Arrays.asList(allTransfers);
    }

    private HttpHeaders authHeaders(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return headers;
    }

}
