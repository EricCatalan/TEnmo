package com.techelevator.tenmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.techelevator.tenmo.dao.AccountDAO;

@SpringBootApplication
public class TenmoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenmoApplication.class, args);
    }

}
