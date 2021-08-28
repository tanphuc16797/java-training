package com.amit.springsercurity.redis.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Data
@RedisHash("amit_token")
public class TokenDTO {
    @Id
    private String token;

    private Long userId;

    private String userName;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;
}
