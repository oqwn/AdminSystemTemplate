package com.oqwn.backend;

import com.oqwn.backend.mapper.UserMapper;
import com.oqwn.backend.model.User;
import com.oqwn.backend.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void testRegisterUser() {
        String result = userService.registerUser("testuser", "testuser@example.com", "1234567890", "password123", "password123");
        System.out.println(result);
    }

    @Test
    void testRegisterUser2() {
        String result = userService.registerUser("testuser", "testu@example.com", "123456780", "password123", "password123");
        System.out.println(result);
    }


}
