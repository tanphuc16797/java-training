package com.amit.springsercurity.redis.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Data
@RedisHash("amit_password")
public class PasswordDTO {
    @Id
    private String password;

    private String encodedPassword;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;
}
