package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserService {

    //List<User> queryAll();

    Map queryAll(Integer page, Integer rows);

    int selectRegistUserNumber(int day);

    List<UserDTO> selectUserDistribution(Integer sex);

    void add(User user);

    User selectByPhone(String phone);
}
