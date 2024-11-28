package com.oqwn.backend.service;

import com.oqwn.backend.mapper.UserMapper;
import com.oqwn.backend.model.User;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(String username, String email, String phone, String password, String confirmPassword) {
        // 校验二次密码是否一致
        if (!password.equals(confirmPassword)) {
            return "两次密码输入不一致";
        }

        // 校验用户名长度
        if (username.length() <= 4) {
            return "用户名长度必须大于 4 个字符";
        }

        // 校验用户名是否包含特殊字符
        if (!username.matches("[a-zA-Z0-9_]+")) {  // 仅允许字母、数字和下划线
            return "用户名不能包含特殊字符";
        }

        // 校验密码长度
        if (password.length() <= 8) {
            return "密码长度必须大于 8 个字符";
        }

        // 检查邮箱和电话是否已被注册
        if (emailExists(email)) {
            return "邮箱已被注册";
        }

        if (phoneExists(phone)) {
            return "电话已被注册";
        }

        // 创建用户对象
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));  // 密码加密
        user.setAvatarUrl("");
        user.setGender("other");  // 默认性别为 "other"
        user.setBio("");  // 默认没有自我描述
        user.setValid(1);  // 账户有效
        user.setDeleted(0);  // 账户未删除

        // 尝试插入数据库
        int rowsAffected = userMapper.insert(user);

        // 检查插入是否成功
        if (rowsAffected > 0) {
            return "注册成功";
        } else {
            return "注册失败，请稍后重试";
        }
    }



    // 检查邮箱是否已存在
    private boolean emailExists(String email) {
        return userMapper.selectCountByEmail(email) > 0;
    }

    // 检查电话是否已存在
    private boolean phoneExists(String phone) {
        return userMapper.selectCountByPhone(phone) > 0;
    }
}
