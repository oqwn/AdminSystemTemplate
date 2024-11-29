package com.oqwn.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oqwn.backend.mapper.UserMapper;
import com.oqwn.backend.model.User;
import com.oqwn.backend.model.request.UserLoginRequest;
import com.oqwn.backend.model.response.LoginResponse;
import com.oqwn.backend.service.UserService;
import com.oqwn.backend.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.oqwn.backend.model.response.UserResponse;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Resource
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    public String registerUser(String username, String email, String phone, String password, String confirmPassword) {
        // Validate that the password and confirmation password match
        if (!password.equals(confirmPassword)) {
            return "The passwords do not match";
        }

        // Validate username length
        if (username.length() <= 4) {
            return "Username must be longer than 4 characters";
        }

        // Validate that the username does not contain special characters
        if (!username.matches("[a-zA-Z0-9_]+")) {  // Only allow letters, numbers, and underscores
            return "Username cannot contain special characters";
        }

        // Validate password length
        if (password.length() <= 8) {
            return "Password must be longer than 8 characters";
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Invalid email format";
        }

        // Validate phone number format (simple validation)
        if (!phone.matches("^(\\+\\d{1,3}[- ]?)?\\d{10}$")) {  // E.g., "+1234567890" or "1234567890"
            return "Invalid phone number format";
        }

        // Check if the email already exists in the database using MyBatis Plus
        QueryWrapper<User> emailQuery = new QueryWrapper<>();
        emailQuery.eq("email", email);
        User existingEmailUser = userMapper.selectOne(emailQuery);
        if (existingEmailUser != null) {
            return "Email is already registered";
        }

        // Check if the phone number already exists in the database using MyBatis Plus
        QueryWrapper<User> phoneQuery = new QueryWrapper<>();
        phoneQuery.eq("phone", phone);
        User existingPhoneUser = userMapper.selectOne(phoneQuery);
        if (existingPhoneUser != null) {
            return "Phone number is already registered";
        }

        // Create a new User object
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));  // Encrypt the password
        user.setAvatarUrl("");  // Set default avatar URL
        user.setGender("other");  // Default gender is "other"
        user.setBio("");  // Default bio is empty
        user.setValid(1);  // Set the account as valid
        user.setDeleted(0);  // Set the account as not deleted

        // Try to insert the new user into the database
        int rowsAffected = userMapper.insert(user);

        // Check if the user was successfully inserted
        if (rowsAffected > 0) {
            return "Registration successful";
        } else {
            return "Registration failed, please try again later";
        }
    }

    public LoginResponse loginUser(UserLoginRequest loginRequest) {
        String account = loginRequest.getAccount();
        String password = loginRequest.getPassword();

        // Try to find the user by email or phone number
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (account.contains("@")) {
            // If account contains "@" symbol, it's likely an email
            queryWrapper.eq("email", account);
        } else {
            // Otherwise, treat it as a phone number
            queryWrapper.eq("phone", account);
        }

        User user = userMapper.selectOne(queryWrapper);
        // If user not found or password does not match, return null (indicating failure)
        if (user == null || !checkPassword(user, password)) {
            return null;
        }

        // Generate JWT token
        String token = jwtTokenUtil.generateToken(user);

        // Create UserResponse object excluding sensitive data
        UserResponse userResponse = new UserResponse(user);

        // Return a response object containing the JWT and user data
        return new LoginResponse(token, userResponse);
    }


    // check whether password is correct
    public Boolean checkPassword(User user, String password) {
        // use BCryptPasswordEncoder to verify the password
        return passwordEncoder.matches(password, user.getPassword());
    }

}
