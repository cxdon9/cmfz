package com.baizhi.mapper;

import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {

    List<User> queryAll(@Param("start") Integer start, @Param("rows") Integer rows);

    int selectRegistUserNumber(int day);

    List<UserDTO> selectUserDistribution(Integer sex);

    void add(User user);

    User selectByphone(String phone);
}
