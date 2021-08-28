package com.amit.springsercurity.service;

import com.amit.springsercurity.database.entity.User;
import com.amit.springsercurity.domain.TokenStore;
import com.amit.springsercurity.domain.UserDomain;
import com.amit.springsercurity.model.ApiException;
import com.amit.springsercurity.model.ERROR;
import com.amit.springsercurity.model.MainResponse;
import com.amit.springsercurity.model.request.LoginRequest;
import com.amit.springsercurity.model.response.LoginResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService extends BaseService{

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);

    @Autowired
    TokenStore tokenStore;

    @Autowired
    UserDomain userDomain;

    public MainResponse<LoginResponse> login(LoginRequest request) throws ApiException{
        // step 1 : validate request

        if (StringUtils.isBlank(request.getUserName())){
            LOGGER.debug("login - user login fail : username blank");
            throw new ApiException(ERROR.INVALID_REQUEST , "Tên đăng nhập không được để trống");
        }

        if (StringUtils.isBlank(request.getPassword())){
            LOGGER.debug("login - user login fail : password blank");
            throw new ApiException(ERROR.INVALID_REQUEST , "Mật khẩu không được để trống");
        }

        User user = userDomain.getUserByUserName(request.getUserName());

        if (user == null){
            LOGGER.debug("login - user login fail : user {} not found" , request.getUserName());
            throw new ApiException(ERROR.INVALID_REQUEST , "Người dùng không tồn tại trên hệ thống");
        }

        if (!passwordEncoder.matches(request.getPassword() , user.getPassword())){
            LOGGER.debug("login - user login fail : user {} password not matching" , request.getUserName());
            throw new ApiException(ERROR.INVALID_REQUEST , "Tên đăng nhập hoặc mật khẩu không đúng, xin vui lòng thử lại!");
        }

        String token = UUID.randomUUID().toString();

        tokenStore.insertToken(token , user.getId() , user.getUserName());

        MainResponse<LoginResponse> response = new MainResponse<>();
        LoginResponse loginData = new LoginResponse();
        loginData.setToken(token);
        loginData.setUserId(user.getId());
        loginData.setName(user.getName());

        response.setData(loginData);
        return response;


    }

}
