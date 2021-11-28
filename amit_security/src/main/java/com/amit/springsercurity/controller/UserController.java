package com.amit.springsercurity.controller;

import com.amit.springsercurity.model.ApiException;
import com.amit.springsercurity.model.MainResponse;
import com.amit.springsercurity.model.request.AddMutilUserRequest;
import com.amit.springsercurity.model.request.AddUserRequest;
import com.amit.springsercurity.model.request.ImportUserByExcelRequest;
import com.amit.springsercurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    public MainResponse<String> createdUser(@RequestBody AddUserRequest request) throws ApiException{
        return userService.createdUser(request);
    }
    @PostMapping("/non-async")
    public MainResponse<Long> createdUsers(@RequestBody AddMutilUserRequest request) throws ApiException{
        return userService.createdMutilUser(request);
    }
    @PostMapping("/async")
    public MainResponse<Long> createdUsersAsync(@RequestBody AddMutilUserRequest request) throws ApiException{
        return userService.createdMutilUserAsync(request);
    }
    @PostMapping("import/excel/async")
    public MainResponse<Long> createdUsersByExcelAsync(@RequestPart("file") MultipartFile file) throws ApiException{
        return userService.importedUserOutboxesByExcel(file);
    }
}
