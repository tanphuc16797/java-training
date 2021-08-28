package com.amit.springsercurity;

import com.amit.springsercurity.util.ICheckBCryptPasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSercurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSercurityApplication.class, args);
    }
    @Bean
    public ICheckBCryptPasswordEncoder passwordEncoder(){
        return new ICheckBCryptPasswordEncoder();
    }

    @Bean("fixPool")
    public ExecutorService fixThreadPool(){
        return Executors.newFixedThreadPool(15);
    }

    @Bean("cachePool")
    public ExecutorService cacheThreadPool(){
        return Executors.newCachedThreadPool();
    }
}
