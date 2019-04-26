package com.baizhi;

import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Menu;
import com.baizhi.entity.User;
import com.baizhi.mapper.BannerDao;
import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
import com.baizhi.service.MenuService;
import com.baizhi.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {
    @Autowired
    private MenuService menuService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        List<Menu> menu = menuService.getMenu();
        for (Menu menu1 : menu) {
            System.out.println(menu1);
        }
    }

    @Test
    public void banner() {
        List<Banner> list = bannerDao.selectAll();
        for (Banner banner : list) {
            System.out.println(banner);
        }
    }

    @Test
    public void Album() {
        List<Album> list = albumService.getAlbum();
        for (Album album : list) {
            System.out.println(album);
        }
    }

    @Test
    public void MD5() {
        String password = "123456";
        String md5Hex = DigestUtils.md5Hex(password);
    }

    @Test
    public void userTest() {
        User user = new User();
        user.setName("987897");
        user.setDharma("niubi");
        /*sex;
    private String province;
    private String city;
    private String sign;
    private Integer status;
    private String phone;
    private String password;
    private String salt;
    private String headImg;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private Master master;*/
        user.setSex(1);
        user.setProvince("江苏");
        user.setCity("南京");
        user.setSign("132165465464");
        user.setPhone("123456789");
        String password = "963857";
        String salt = UUID.randomUUID().toString().replace("-", "").substring(3, 7);
        String pasw = DigestUtils.md5Hex(password + salt);
        user.setSalt(salt);
        user.setPassword(pasw);
        userService.add(user);
    }

    @Test
    public void userTest1() {
        User user = userService.selectByPhone("123456789");
        String password = user.getPassword();
        System.out.println(password.equals(DigestUtils.md5Hex("963857" + user.getSalt())));
    }

    @Test
    public void userTest2() {

    }

    @Test
    public void userTest3() {

    }
}
