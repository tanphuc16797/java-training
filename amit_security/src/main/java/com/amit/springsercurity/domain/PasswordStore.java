package com.amit.springsercurity.domain;

import com.amit.springsercurity.redis.entity.PasswordDTO;
import com.amit.springsercurity.redis.entity.TokenDTO;
import com.amit.springsercurity.redis.repository.PasswordRepository;
import com.amit.springsercurity.redis.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PasswordStore {
    @Autowired
    PasswordRepository passwordRepository;

    @Value("${password.time-to-live}")
    long tokenTimeToLive;


    public void insertPassword(String password , String encodedPassword ){
        PasswordDTO PasswordDTO = new PasswordDTO();
        PasswordDTO.setPassword(password);
        PasswordDTO.setEncodedPassword(encodedPassword);
        PasswordDTO.setTimeToLive(tokenTimeToLive);
        passwordRepository.save(PasswordDTO);

    }

    public Optional<PasswordDTO> getEncodedPassword(String password){
        return passwordRepository.findById(password);
    }

}
