package com.oqwn.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oqwn.backend.model.User;
import com.oqwn.backend.model.request.UserLoginRequest;
import com.oqwn.backend.model.response.LoginResponse;

public interface UserService extends IService<User> {
    /**
     *
     * @param username username
     * @param email email
     * @param phone phone number
     * @param password password
     * @param confirmPassword second password
     * @return status code
     */
    String registerUser(String username, String email, String phone, String password, String confirmPassword);

    /**
     *
     * @param loginRequest
     * @return
     */
    public LoginResponse loginUser(UserLoginRequest loginRequest);

}
