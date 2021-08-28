package com.amit.springsercurity.model.request;

import lombok.Data;

import java.util.List;

@Data
public class AddMutilUserRequest {
    private List<AddUserRequest> data;
}
