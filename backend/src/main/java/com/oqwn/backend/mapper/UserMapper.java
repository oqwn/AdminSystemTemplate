package com.oqwn.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oqwn.backend.model.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    // 根据邮箱查询用户数量
    int selectCountByEmail(String email);

    // 根据电话查询用户数量
    int selectCountByPhone(String phone);
}
