package com.amit.springsercurity.database.repository;

public interface CustomUserRepository{
    void importUsers(String insertScript);
    void importUserOutboxes(String insertScript);
}
