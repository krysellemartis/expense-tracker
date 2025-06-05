package com.finance.expense.tracker.delegate;

import com.finance.expense.tracker.api.LoginApiDelegate;
import com.finance.expense.tracker.model.LoginRequest;
import com.finance.expense.tracker.model.LoginResponse;
import com.finance.expense.tracker.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginApiDelegateImpl implements LoginApiDelegate {

    @Autowired
    LoginService getLoginService;

    @Override
    public ResponseEntity<LoginResponse> getUser(LoginRequest loginRequest) {
        return ResponseEntity.ok(getLoginService.getUser(loginRequest.getEmail(), loginRequest.getPassword()));
    }

}
