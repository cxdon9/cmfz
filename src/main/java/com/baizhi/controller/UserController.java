package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /*@RequestMapping("/queryAll")
    @ResponseBody
    public List<User> queryAll() {
        List<User> list = userService.queryAll();
        return list;
    }*/
    @RequestMapping("/queryAll")
    @ResponseBody
    public Map queryAll(Integer page, Integer rows) {
        Map map = userService.queryAll(page, rows);
        return map;
    }


    @RequestMapping("/selectNearlyThreeWeekRegistUser")
    @ResponseBody
    public Map selectNearlyThreeWeekRegistUser() {
        Map map = new HashMap();
        map.put("a", userService.selectRegistUserNumber(7));
        map.put("b", userService.selectRegistUserNumber(14));
        map.put("c", userService.selectRegistUserNumber(21));
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map add(User user, MultipartFile file) {
        //1.获取源文件夹
        String oldName = file.getOriginalFilename();
        //2.将接收的文件复制到服务器上
        String uuid = UUID.randomUUID().toString();

        String newname = uuid + oldName.substring(oldName.lastIndexOf("."));
        user.setHeadImg(newname);
        /*password, salt*/
        String password = user.getPassword();
        String salt = UUID.randomUUID().toString().replace("-", "").substring(3, 7);
        String pasw = DigestUtils.md5Hex(password + salt);
        user.setSalt(salt);
        user.setPassword(pasw);
        try {
            file.transferTo(new File("D:\\IdealCode2\\cmfz\\src\\main\\webapp\\img\\userHead\\" + newname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        userService.add(user);
        Map map = new HashMap();
        map.put("a", userService.selectRegistUserNumber(7));
        map.put("b", userService.selectRegistUserNumber(14));
        map.put("c", userService.selectRegistUserNumber(21));
        return map;
    }


    @RequestMapping("/selectMaleDistribution")
    @ResponseBody
    public List<UserDTO> selectMaleDistribution() {
        List<UserDTO> list = userService.selectUserDistribution(1);
        return list;
    }


    @RequestMapping("/selectFemaleDistribution")
    @ResponseBody
    public List<UserDTO> selectFemaleDistribution() {
        List<UserDTO> list = userService.selectUserDistribution(0);
        return list;
    }
}
