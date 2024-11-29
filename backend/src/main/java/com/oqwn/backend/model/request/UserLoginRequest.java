package com.oqwn.backend.model.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String account;
    private String password;
}
