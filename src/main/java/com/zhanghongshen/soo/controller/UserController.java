package com.zhanghongshen.soo.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhanghongshen.soo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public JSONObject login(@RequestParam Map<String,String> params) {
        String username = params.get("username");
        String password = params.get("password");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",userService.check(username,password));
        return jsonObject;
    }
}
