package com.techelevator.tenmo.DAO;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcTransferDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
