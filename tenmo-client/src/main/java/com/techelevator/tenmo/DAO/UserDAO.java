package com.techelevator.tenmo.DAO;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDAO {

    public List<User> getAllUsers();

    public User getUserByID(int id);

}
