package com.amit.springsercurity.model.request;

import lombok.Data;

@Data
public class AddUserRequest {
    private String userName;
    private String password;
    private String name;
    private Integer age;
}
