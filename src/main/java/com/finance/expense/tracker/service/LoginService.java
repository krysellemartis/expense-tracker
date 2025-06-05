package com.finance.expense.tracker.service;

import com.finance.expense.tracker.model.LoginRequest;
import com.finance.expense.tracker.model.LoginResponse;

public interface LoginService {
    LoginResponse getUser(String email, String password);
    LoginResponse createUser(LoginRequest loginRequest);
}
