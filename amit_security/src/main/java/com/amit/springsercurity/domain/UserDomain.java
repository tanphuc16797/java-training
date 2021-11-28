package com.amit.springsercurity.domain;

import com.amit.springsercurity.database.entity.User;
import com.amit.springsercurity.database.repository.CustomUserRepository;
import com.amit.springsercurity.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDomain {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserRepository customUserRepository;

    public User getUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void insertUsers(String sqlString) {customUserRepository.importUsers(sqlString); }

    public void insertUserOutboxes(String sqlString) {customUserRepository.importUserOutboxes(sqlString); }


}
