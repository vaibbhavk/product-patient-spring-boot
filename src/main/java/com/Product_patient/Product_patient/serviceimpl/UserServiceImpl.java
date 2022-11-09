package com.Product_patient.Product_patient.serviceimpl;

import com.Product_patient.Product_patient.exception.BadRequestException;
import com.Product_patient.Product_patient.model.User;
import com.Product_patient.Product_patient.repository.UserRepository;
import com.Product_patient.Product_patient.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.regex.Pattern;

/*
 * Patient Service Implementation
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public User addUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (user.getFirstName() != null && user.getLastName() != null && user.getEmail() != null && user.getContact() != null && user.getPassword() != null

        ) {
            if (!userService.userExists(user.getEmail())) {
                if (user.getContact().length() < 10 || isNumber(user.getFirstName()) || isNumber(user.getLastName()) || isNumber(user.getEmail()) || user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getEmail().isBlank()) {
                    throw new BadRequestException("Please enter valid user details");
                } else {
                    log.info("User Details saved");


                    user.setPassword(generateHash(user.getPassword(), user.getEmail()));

                    return userRepository.save(user);
                }
            } else {
                log.warn("Details Exist");
                throw new BadRequestException("User id already exists!");
            }
        } else {
            throw new BadRequestException("User Details should not be null");
        }
    }


    @Override
    public Map<String, String> loginUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Optional<User> foundUser = userRepository.findByMail(user.getEmail());

        if (foundUser.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        User dbUser = foundUser.get();

        if (!checkHash(user.getPassword(), dbUser.getPassword(), dbUser.getEmail())) {
            throw new BadRequestException("Invalid credentials");
        }

        log.info("Matches");

        String jwtSecret = "uthruightmruihtngurtugt5erur7899849242463643dfgfdthisIsTheJwtSecretioehirniweuir89e9tgtgtvenigvrienviiu";

        String token = Jwts.builder().setSubject(dbUser.getUserId().toString()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 600000)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();


        Map<String, String> tk = new HashMap<>();
        tk.put("token", token);

        return tk;
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.findByMail(email).isPresent();
    }

    private boolean isNumber(String userDetail) {
        return Pattern.matches("[0-9]+", userDetail);
    }

    public String generateHash(String inputPassword, String inputEmail) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] salt = inputEmail.getBytes();


        PBEKeySpec pbeKeySpec = new PBEKeySpec(inputPassword.toCharArray(), salt, 10, 512);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hash = skf.generateSecret(pbeKeySpec).getEncoded();

        //converting to string to store into database
        String base64Hash = Base64.getMimeEncoder().encodeToString(hash);

        log.info(base64Hash);

        return base64Hash;
    }

    public boolean checkHash(String entered, String savedHash, String savedEmail) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] salt = savedEmail.getBytes();


        PBEKeySpec pbeKeySpec = new PBEKeySpec(entered.toCharArray(), salt, 10, 512);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hash = skf.generateSecret(pbeKeySpec).getEncoded();

        String base64Hash2 = Base64.getMimeEncoder().encodeToString(hash);

        log.info(base64Hash2);

        log.info("check if hashes match, result: {}", savedHash.equals(base64Hash2));

        return savedHash.equals(base64Hash2);
    }

}
