package com.oqwn.backend;

import com.oqwn.backend.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Resource
    private UserServiceImpl userService;

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
