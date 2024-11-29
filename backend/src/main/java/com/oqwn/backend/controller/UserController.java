package com.oqwn.backend.controller;

import com.oqwn.backend.model.User;
import com.oqwn.backend.model.request.UserLoginRequest;
import com.oqwn.backend.model.request.UserRegisterRequest;
import com.oqwn.backend.model.response.ApiResponse;
import com.oqwn.backend.model.response.UserResponse;
import com.oqwn.backend.model.response.LoginResponse;
import com.oqwn.backend.service.UserService;
import com.oqwn.backend.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    // register interface api
    @PostMapping("/register")
    public ApiResponse<?> registerUser(@RequestBody UserRegisterRequest request) {

        String result = userService.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPhone(),
                request.getPassword(),
                request.getConfirmPassword()
        );

        if ("Registration successful".equals(result)) {
            return new ApiResponse<>("success", HttpStatus.OK.value(), "Registration successful");
        } else {
            return new ApiResponse<>("error", HttpStatus.BAD_REQUEST.value(), result);
        }
    }

    // 用户登录接口
    @PostMapping("/login")
    public ApiResponse<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        // Call the service layer to handle login
        LoginResponse loginResponse = userService.loginUser(loginRequest);

        // Return the response from the service layer
        if (loginResponse != null) {
            return new ApiResponse<>("success", HttpStatus.OK.value(), "Login successful", loginResponse);
        }

        return new ApiResponse<>("error", HttpStatus.UNAUTHORIZED.value(), "Invalid username or password");
    }


}
