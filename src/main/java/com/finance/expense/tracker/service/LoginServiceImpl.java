package com.finance.expense.tracker.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.finance.expense.tracker.model.LoginRequest;
import com.finance.expense.tracker.model.LoginResponse;
import com.finance.expense.tracker.model.LoginResponseData;
import com.finance.expense.tracker.repository.ExpenseTrackerRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    ExpenseTrackerRepository expenseTrackerRepository;

    @Override
    public LoginResponse getUser(String email, String password) {
        String displayName = expenseTrackerRepository.getUserName(email, password);
        var loginResponse = new LoginResponse();
        var loginResponseData = new LoginResponseData();
        try {
            loginResponseData.setMessage(StringUtils.isEmpty(displayName) ? "User does not exist" : "Login successful");
            loginResponseData.setStatus(StringUtils.isEmpty(displayName) ? "error" : "success");
            loginResponseData.setToken(StringUtils.isEmpty(displayName) ? "error" : createJWT(generateSecretKey(), displayName));
        } catch (Exception e) {
            loginResponseData.setMessage(String.format("Error loggin in user %s: %s", email, e.getMessage()));
            loginResponseData.setStatus("failure");
        }
        loginResponse.setData(loginResponseData);
        return loginResponse;
    }

    @Override
    public LoginResponse createUser(LoginRequest loginRequest) {
        var loginResponse = new LoginResponse();
        var loginResponseData = new LoginResponseData();
        String userName = loginRequest.getFullName();
        try {
            if(!expenseTrackerRepository.checkUserExists(loginRequest.getEmail())) {
                expenseTrackerRepository.createUser(userName, loginRequest.getEmail(), loginRequest.getPassword());
                loginResponseData.setMessage(String.format("Created user %s successfully", userName));
                loginResponseData.setStatus("success");
                loginResponseData.setToken(createJWT(generateSecretKey(), userName));
            }
            else {
                loginResponseData.setMessage(String.format("User email %s already exists", loginRequest.getEmail()));
                loginResponseData.setStatus("error");
            }
        }
        catch (Exception e) {
            loginResponseData.setMessage(String.format("Error creating user %s: %s", userName, e.getMessage()));
            loginResponseData.setStatus("failure");
        }
        loginResponse.setData(loginResponseData);
        return loginResponse;
    }

    public static String generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public static String createJWT(String secretKey, String userName) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withClaim("name", userName)
                .withIssuer("expense-tracker")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // Expires in 1 hour
                .sign(algorithm);
    }

}
