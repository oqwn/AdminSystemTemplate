package com.oqwn.backend.model.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private UserResponse user;
    public LoginResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }
}
