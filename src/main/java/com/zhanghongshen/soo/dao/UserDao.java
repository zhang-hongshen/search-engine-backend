package com.zhanghongshen.soo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhanghongshen.soo.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
