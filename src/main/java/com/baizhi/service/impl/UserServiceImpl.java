package com.baizhi.service.impl;

import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.mapper.UserDao;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /*@Override
    public List<User> queryAll() {
        return userDao.queryAll();
    }*/

    @Override
    public Map queryAll(Integer page, Integer rows) {
        Map map = new HashMap();
        List<User> users = userDao.queryAll(page * rows - rows, rows);
        map.put("rows", users);
        map.put("total", userDao.selectAll().size());
        return map;
    }

    @Override
    public int selectRegistUserNumber(int day) {
        return userDao.selectRegistUserNumber(day);
    }

    @Override
    public List<UserDTO> selectUserDistribution(Integer sex) {
        return userDao.selectUserDistribution(sex);
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public User selectByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        List<User> users = userDao.select(user);
        return users.get(0);
    }
}
