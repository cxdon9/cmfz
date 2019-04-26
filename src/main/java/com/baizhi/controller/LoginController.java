package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.mapper.UserDao;
import com.baizhi.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "account", produces = "text/plain;charset=UTF-8")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @RequestMapping("login")
    public Object login(String phone, String password) {
        if (password == null || phone == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "-200");
            map.put("errmsg", "请输入用户名和密码");
            return map;
        } else {
            User user = userService.selectByPhone(phone);
            String salt = user.getSalt();
            String s = user.getPassword();
            String s2 = DigestUtils.md5Hex(password + salt);
            if (s.equals(s2)) {
                return user;
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("error", "-200");
                map.put("errmsg", "密码错误");
                return map;
            }
        }
    }

    @RequestMapping("regist")
    public Object regist(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        User user1 = userDao.selectByphone(phone);
        if (password == null || phone == null) {
            map.put("error", "-200");
            map.put("errmsg", "请输入手机号和密码");
            return map;
        } else if (user1 == null) {
            User user = new User();
            user.setPhone(phone);
            String salt = UUID.randomUUID().toString().replace("-", "").substring(3, 7);
            String pasw = DigestUtils.md5Hex(password + salt);
            user.setSalt(salt);
            user.setPassword(pasw);
            userDao.insert(user);
            return user;
        } else {
            map.put("error", "-200");
            map.put("errmsg", "该手机号已经存在");
            return map;
        }
    }
}
