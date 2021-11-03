package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;



public class UserService {

    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;
    public void setAuthToken(String authToken){this.authToken = authToken;}


    public UserService(String url) {this.baseUrl = url;}

    public User getUser(int id) {
        User user = null;

        try{
            user = restTemplate.getForObject(baseUrl + id, User.class);
        } catch(ResourceAccessException ex) {
            System.out.println("The user could not be found");
        }

        return user;
    }
//Going to have to update URL address
    public Account getAccountBalance(int userID){
        Account account = null;
        try{
            ResponseEntity<Account> response = restTemplate.exchange(baseUrl, HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        }catch(RestClientResponseException | ResourceAccessException e){}
    return account;}

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }




}
