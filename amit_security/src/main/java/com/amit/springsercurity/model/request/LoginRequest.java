package com.amit.springsercurity.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
