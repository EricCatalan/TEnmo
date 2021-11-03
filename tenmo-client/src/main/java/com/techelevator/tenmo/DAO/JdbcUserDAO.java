package com.techelevator.tenmo.DAO;

import javax.sql.DataSource;


import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;


public class JdbcUserDAO implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();

        String sql = "SELECT user_id, username FROM users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            User userResults = mapRowToUser(results);
            allUsers.add(userResults);
        }

        return allUsers;
    }

    @Override
    public User getUserByID(int id) {
        User user = null;

        String sql = "SELECT username FROM users";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if(result.next()) {
            user = mapRowToUser(result);
        }
        return user;
    }

    private User mapRowToUser(SqlRowSet results) {
        User user = new User();
        user.setId(results.getInt("user_id"));
        user.setUsername(results.getString("username"));
        return user;
    }
}
