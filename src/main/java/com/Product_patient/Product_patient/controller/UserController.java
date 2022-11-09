package com.Product_patient.Product_patient.controller;

import com.Product_patient.Product_patient.model.*;
import com.Product_patient.Product_patient.serviceimpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /*
     * Create a new user
     */
    @PostMapping(value = "/user/register", consumes = "application/json")
    public User createUser(@Valid @RequestBody User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("In create user method");
        return userServiceImpl.addUser(user);
    }

    /*
     * Login a user
     */
    @PostMapping(value = "/user/login", consumes = "application/json")
    public Map<String, String> checkUser(@Valid @RequestBody User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        log.info("In login user method");
        return userServiceImpl.loginUser(user);
    }


}




