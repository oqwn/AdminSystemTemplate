package com.oqwn.backend.model;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("users")
public class User {

    @TableId(type = IdType.AUTO)  // 自动增长策略
    private Long id;

    private String username;  // 用户名
    private String email;     // 邮箱
    private String phone;     // 电话
    private String password;  // 密码

    @TableField("avatar_url")
    private String avatarUrl;  // 头像URL

    private String gender;     // 性别

    private String bio;        // 自我描述

    @TableField(fill = FieldFill.INSERT)
    private String createdAt;  // 创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedAt;  // 更新时间

    private Integer valid;     // 是否有效

    @TableLogic
    private Integer deleted;   // 是否已删除
}
