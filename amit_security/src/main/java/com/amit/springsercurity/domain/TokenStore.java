package com.amit.springsercurity.domain;

import com.amit.springsercurity.redis.entity.TokenDTO;
import com.amit.springsercurity.redis.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TokenStore {
    @Autowired
    TokenRepository tokenRepository;

    @Value("${token.time-to-live}")
    long tokenTimeToLive;


    public void insertToken(String token , long userId , String userName ){
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setUserId(userId);
        tokenDTO.setTimeToLive(tokenTimeToLive);
        tokenDTO.setUserName(userName);
        tokenRepository.save(tokenDTO);

    }

    public Optional<TokenDTO> getSessionFromToken(String token){
        return tokenRepository.findById(token);
    }

}
