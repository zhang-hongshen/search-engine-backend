package com.zhanghongshen.soo.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/30
 */
@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @Column(name = "username")
    private String username;


    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "created_time",updatable = false)
    @CreationTimestamp
    private Date createTime;

    @Column(name = "last_login_time")
    @UpdateTimestamp
    private Date lastLoginTime;

    public User() {

    }

    public User(String username, String password, Date createTime, Date updateTime) {
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.lastLoginTime = updateTime;
    }
}
