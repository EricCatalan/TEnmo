package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class UserService {

    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();


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

    public void getBalance() {

    }

}
