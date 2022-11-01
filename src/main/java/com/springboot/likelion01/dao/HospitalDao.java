package com.springboot.likelion01.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class HospitalDao {

    private final JdbcTemplate jdbcTemplate;

    public HospitalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
