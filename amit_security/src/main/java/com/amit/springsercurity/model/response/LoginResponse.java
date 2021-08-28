package com.amit.springsercurity.model.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long userId;
    private String name;
}
