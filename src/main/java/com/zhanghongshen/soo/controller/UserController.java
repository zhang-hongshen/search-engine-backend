package com.zhanghongshen.soo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.zhanghongshen.soo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/30
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultBean login(@RequestBody LinkedHashMap<String,String> params) {
        String username = params.get("username");
        String password = params.get("password");
        System.out.println(params);
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(userService.check(username,password) ? ResultBean.SUCCESS : ResultBean.FAIL);
        return resultBean;
    }
}
