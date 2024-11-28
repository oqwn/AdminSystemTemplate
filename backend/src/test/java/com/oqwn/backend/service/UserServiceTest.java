package com.oqwn.backend.service;

import com.oqwn.backend.mapper.UserMapper;
import com.oqwn.backend.model.User;
import com.oqwn.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        // 初始化 Mockito 注解
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // 模拟插入数据库成功
        when(userMapper.insert(any(User.class))).thenReturn(1);  // 插入成功返回 1

        // 模拟邮箱和电话未注册
        when(userMapper.selectCountByEmail("testuser@example.com")).thenReturn(0);
        when(userMapper.selectCountByPhone("1234567890")).thenReturn(0);

        String result = userService.registerUser("testuser", "testuser@example.com", "1234567890", "password123", "password123");
        assertEquals("注册成功", result);  // 假设插入成功，id 生成 1
    }

    @Test
    void testRegisterUser_PasswordMismatch() {
        String result = userService.registerUser("testuser", "testuser@example.com", "1234567890", "password123", "password321");
        assertEquals("两次密码输入不一致", result);
    }

    @Test
    void testRegisterUser_UsernameTooShort() {
        String result = userService.registerUser("abc", "testuser@example.com", "1234567890", "password123", "password123");
        assertEquals("用户名长度必须大于 4 个字符", result);
    }

    @Test
    void testRegisterUser_InvalidUsername() {
        String result = userService.registerUser("test@user", "testuser@example.com", "1234567890", "password123", "password123");
        assertEquals("用户名不能包含特殊字符", result);
    }

    @Test
    void testRegisterUser_PasswordTooShort() {
        String result = userService.registerUser("testuser", "testuser@example.com", "1234567890", "pass", "pass");
        assertEquals("密码长度必须大于 8 个字符", result);
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        // 模拟邮箱已被注册
        when(userMapper.selectCountByEmail("testuser@example.com")).thenReturn(1);

        String result = userService.registerUser("testuser", "testuser@example.com", "1234567890", "password123", "password123");
        assertEquals("邮箱已被注册", result);
    }

    @Test
    void testRegisterUser_PhoneAlreadyExists() {
        // 模拟电话已被注册
        when(userMapper.selectCountByPhone("1234567890")).thenReturn(1);

        String result = userService.registerUser("testuser", "testuser@example.com", "1234567890", "password123", "password123");
        assertEquals("电话已被注册", result);
    }

    @Test
    void testRegisterUser_InsertFailure() {
        // 模拟插入失败
        when(userMapper.insert(any(User.class))).thenReturn(0);  // 插入失败

        // 模拟邮箱和电话未注册
        when(userMapper.selectCountByEmail("testuser@example.com")).thenReturn(0);
        when(userMapper.selectCountByPhone("1234567890")).thenReturn(0);

        String result = userService.registerUser("testuser", "testuser@example.com", "1234567890", "password123", "password123");
        assertEquals("注册失败，请稍后重试", result);
    }
}
