package com.oqwn.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oqwn.backend.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
