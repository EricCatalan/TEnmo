package com.techelevator.tenmo.services;

import com.techelevator.tenmo.DAO.JdbcUserDAO;
import com.techelevator.tenmo.model.*;
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

    public List<User> listAllUsers(String authToken) {
        User[] allUsers = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "users/all", HttpMethod.GET,makeAuthEntity(authToken),User[].class);
            allUsers = response.getBody();
        } catch(RestClientResponseException | ResourceAccessException e) {}

        return Arrays.asList(allUsers);
    }

    public List<User> listOtherUsers(String authToken) {
        User[] allOtherUsers = null;
        try{
        ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "users/all_non_active", HttpMethod.GET,makeAuthEntity(authToken),User[].class);
        allOtherUsers = response.getBody();
        } catch(RestClientResponseException | ResourceAccessException e){}

        return Arrays.asList(allOtherUsers);
    }

    public List<Account> listUserAccounts(String authToken) {
        Account[] allAccounts = restTemplate.exchange(baseUrl + "accounts", HttpMethod.GET, makeAuthEntity(authToken), Account[].class).getBody();
        return Arrays.asList(allAccounts);
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

    public User getUser(Integer userId, List<User> userList) {
        User user = null;

        for(User currentUser: userList) {
            if(userId.intValue() == currentUser.getId().intValue()) {
                user = currentUser;
            }
        }

        return user;
    }

    public Account getAccountByUserID(Integer userID, List<Account> accountList) {
        Account account = null;
        for(Account currentAccount: accountList) {
            if (userID == currentAccount.getUserID()) {
                account = currentAccount;
            }
        } return account;
    }

    public boolean isUserIdValid(Integer userID, List<User> userList) {
        boolean isValid = false;
        for(User currentUser: userList) {
            if(currentUser.getId().intValue() == userID.intValue()) {
                return isValid = true;
            }
        } return isValid;
    }

    public Integer getUserIDByAccountID(Integer accountID, List<Account> accountList) {
        Integer userID = 0;
        for(Account account: accountList) {
            if(account.getAccountID().intValue() == accountID.intValue()) {
                userID = account.getUserID();
            }
        }
        return userID;
    }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }




}
