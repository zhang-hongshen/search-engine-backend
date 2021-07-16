package com.zhanghongshen.soo.dao;

import com.zhanghongshen.soo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface UserDao extends JpaRepository<User,String> {

    @Query(nativeQuery = true,value = "select * from user where username =?1")
    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update user set last_login_time =?2 where username =?1")
    void updateLastLoginTimeByUsername(String username,Date date);
}
