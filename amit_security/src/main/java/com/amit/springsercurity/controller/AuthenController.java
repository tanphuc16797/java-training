package com.amit.springsercurity.controller;

import com.amit.springsercurity.model.ApiException;
import com.amit.springsercurity.model.MainResponse;
import com.amit.springsercurity.model.request.LoginRequest;
import com.amit.springsercurity.model.response.LoginResponse;
import com.amit.springsercurity.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authencation")
public class AuthenController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public MainResponse<LoginResponse> login(@RequestBody LoginRequest request) throws ApiException{
        return authenticationService.login(request);
    }
}
