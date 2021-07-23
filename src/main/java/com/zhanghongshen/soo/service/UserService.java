package com.zhanghongshen.soo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhanghongshen.soo.dao.UserDao;
import com.zhanghongshen.soo.pojo.entity.User;
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

    /**
     * 检验用户名和密码是否正确
     * @param username
     * @param password
     * @return
     */
    public boolean check(String username,String password){
       User user = findByUsername(username);
       if(user == null){
           return false;
       }
       if(user.getPassword().equals(password)){
           //更新用户登录时间
           updateByUsername(username);
           return true;
       }
       return false;
    }

    private User findByUsername(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return userDao.selectOne(queryWrapper);
    }

    private void updateByUsername(String username){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",username)
                .set("last_login_time", new Date());
        userDao.update(null,updateWrapper);
    }
}
