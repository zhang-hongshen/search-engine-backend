package com.zhanghongshen.soo.service;

import com.zhanghongshen.soo.dao.UserDao;
import com.zhanghongshen.soo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/30
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public boolean check(String username,String password){
       User user = userDao.findByUsername(username);
       if(user == null){
           return false;
       }
       if(user.getPassword().equals(password)){
           //更新用户登录时间
           userDao.updateLastLoginTimeByUsername(username,new Date());
           return true;
       }
       return false;
    }
}
