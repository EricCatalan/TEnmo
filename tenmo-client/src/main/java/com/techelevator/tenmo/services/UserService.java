package com.techelevator.tenmo.services;

import com.techelevator.tenmo.DAO.JdbcUserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import org.springframework.http.*;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserService {

    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    AuthenticatedUser authenticatedUser = new AuthenticatedUser();

    private String authToken = authenticatedUser.getToken();
    private JdbcUserDAO jdbcUserDAO;


    public UserService(String url) {this.baseUrl = url;}

    public void sendMoney(String authToken,Integer sendingToID, Double sendingAmount){
        try{
            ResponseEntity<User> response = restTemplate.exchange(baseUrl + "users/{id}/account",HttpMethod.PUT, makeAuthEntity(authToken),User.class );
        }catch (RestClientResponseException | ResourceAccessException e){}

    }

    public void removeMoney(String authToken,Double sendingAmount){
        try{
            ResponseEntity<User> response = restTemplate.exchange(baseUrl+"users/{id}/account", HttpMethod.PUT, makeAuthEntity(authToken),User.class);
        }catch(RestClientResponseException | ResourceAccessException e){}
    }

    public User[] listUsers (String authToken){
        User[] allUsers = null;
        try{
        ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "users/all", HttpMethod.GET,makeAuthEntity(authToken),User[].class);
        allUsers= response.getBody();
        }catch(RestClientResponseException | ResourceAccessException e){}
        return allUsers;
    }
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
    public Double getAccountBalance(String authToken){
        Double accountBalance = 0.00;
        try{
            ResponseEntity<Double> response = restTemplate.exchange(baseUrl + "balance", HttpMethod.GET, makeAuthEntity(authToken), Double.class);
            accountBalance = response.getBody();
        }catch(RestClientResponseException | ResourceAccessException e){

        }

        return accountBalance;
    }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }




}
