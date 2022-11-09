package com.Product_patient.Product_patient.service;

import com.Product_patient.Product_patient.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public interface UserService {
    public User addUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException;

    public Map<String, String> loginUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException;

    public boolean userExists(String email);

    public String generateHash(String password, String email) throws NoSuchAlgorithmException, InvalidKeySpecException;

}
