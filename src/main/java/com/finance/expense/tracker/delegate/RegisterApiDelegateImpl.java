package com.finance.expense.tracker.delegate;

import com.finance.expense.tracker.api.RegisterApiDelegate;
import com.finance.expense.tracker.model.LoginRequest;
import com.finance.expense.tracker.model.LoginResponse;
import com.finance.expense.tracker.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterApiDelegateImpl implements RegisterApiDelegate {

    @Autowired
    LoginService loginService;

    @Override
    public ResponseEntity<LoginResponse> createUser(LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.createUser(loginRequest));
    }
}
