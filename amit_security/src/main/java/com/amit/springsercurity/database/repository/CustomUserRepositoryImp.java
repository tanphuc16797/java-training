package com.amit.springsercurity.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class CustomUserRepositoryImp implements CustomUserRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Value("${spring.jpa.properties.hibernate.default_schema}")
    String defaultSchema;

    @Override
    @Transactional
    public void importUsers(String insertScript) {
        jdbcTemplate.execute(String.format("INSERT INTO %s.user(username, password, name, age) VALUES %s;", defaultSchema, insertScript));
    }

    @Override
    @Transactional
    public void importUserOutboxes(String insertScript) {
        jdbcTemplate.execute(String.format("INSERT INTO %s.user(username, password, name, age) VALUES %s;", defaultSchema, insertScript));
    }
}
