package com.amit.springsercurity.service;

import com.amit.springsercurity.util.ICheckBCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {

    @Autowired
    protected ICheckBCryptPasswordEncoder passwordEncoder;
}
