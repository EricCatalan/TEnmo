package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import net.minidev.json.JSONUtil;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
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
        ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfers/execute", HttpMethod.POST, entity, Transfer.class);
    }

    public void requestTransfer(Transfer transfer, String authToken) {
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, authHeaders(authToken));
        ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfers/request", HttpMethod.POST, entity, Transfer.class);
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

    public Transfer getTransferByID(int transferID, List<Transfer> allTransfers){
        Transfer transfer = null;
        for(Transfer currentTransfer: allTransfers){
            if(transferID == currentTransfer.getTransferID()){
                transfer = currentTransfer;
            }
        }return transfer;

    }

    public List<Transfer> listAllTransfers(String authToken){
        HttpEntity<Transfer> entity = new HttpEntity<>(authHeaders(authToken));
        Transfer[] allTransfers = restTemplate.exchange(baseUrl +"transfers", HttpMethod.GET,entity, Transfer[].class).getBody();
        return Arrays.asList(allTransfers);
    }

    public List<Transfer> listPendingTransfers(String authToken){
        HttpEntity<Transfer> entity = new HttpEntity<>(authHeaders(authToken));
        Transfer[] allTransfers = restTemplate.exchange(baseUrl +"transfers/pending", HttpMethod.GET,entity, Transfer[].class).getBody();
        return Arrays.asList(allTransfers);
    }

    public boolean isTransferIdValid(Integer transferId, List<Transfer> transferList) {
        boolean isValid = false;
        for(Transfer currentTransfer: transferList) {
            if(currentTransfer.getTransferID().intValue() == transferId.intValue()) {
                return isValid = true;
            }
        } return isValid;
    }

    private HttpHeaders authHeaders(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return headers;
    }

}
