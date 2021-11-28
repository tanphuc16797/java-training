package com.amit.springsercurity.redis.entity;

import com.amit.springsercurity.util.Constant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

@Data
@RedisHash(Constant.REDIS_HASH.ACTIVITY)
public class Activity {
    @Id
    private String login;

    private Boolean isMaster;

    private Date joinAt;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;
}
