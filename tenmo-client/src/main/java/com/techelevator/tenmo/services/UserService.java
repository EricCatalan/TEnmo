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
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class UserService {

    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    AuthenticatedUser authenticatedUser = new AuthenticatedUser();

    private String authToken = authenticatedUser.getToken();
    private JdbcUserDAO jdbcUserDAO;


    public UserService(String url) {this.baseUrl = url;}

//    public void sendMoney(Integer sendingToID, Double sendingAmount, String authToken){
//        try{
//            ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "account/transfer",HttpMethod.PUT, makeAuthEntity(authToken),Account.class );
//        } catch (RestClientResponseException | ResourceAccessException e){}
//
//    }
//
//    public void removeMoney(String authToken, Double sendingAmount){
//        try{
//            ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "account/transfer", HttpMethod.PUT, makeAuthEntity(authToken), Account.class);
//        } catch(RestClientResponseException | ResourceAccessException e){}
//    }

    public List<User> listUsers(String authToken) {
        User[] allUsers = null;
        try{
        ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "users/all", HttpMethod.GET,makeAuthEntity(authToken),User[].class);
        allUsers = response.getBody();
        } catch(RestClientResponseException | ResourceAccessException e){}

        return Arrays.asList(allUsers);
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

    public List<Account> listUserAccounts(String authToken) {
        Account[] allAccounts = restTemplate.exchange(baseUrl + "accounts", HttpMethod.GET, makeAuthEntity(authToken), Account[].class).getBody();
//        try {
//            ResponseEntity<Account[]> response =
//            allAccounts = response.getBody();
//        } catch(RestClientResponseException | ResourceAccessException e){}

        return Arrays.asList(allAccounts);
    }

    public Account getAccountByUserID(Integer userID, List<Account> accountList) {
        Account account = null;
        for(Account currentAccount: accountList) {
            if (userID == currentAccount.getUserID()) {
                account = currentAccount;
            }
        } return account;
    }

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
