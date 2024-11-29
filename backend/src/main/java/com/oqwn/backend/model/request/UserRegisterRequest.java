package com.oqwn.backend.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest {

    private String username;

    private String email;

    private String phone;

    private String password;

    private String confirmPassword;
}

