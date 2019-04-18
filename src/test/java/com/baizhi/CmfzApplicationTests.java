package com.baizhi;

import com.baizhi.entity.Banner;
import com.baizhi.entity.Menu;
import com.baizhi.service.BannerService;
import com.baizhi.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {
    @Autowired
    private MenuService menuService;
    @Autowired
    private BannerService bannerService;

    @Test
    public void contextLoads() {
        List<Menu> menu = menuService.getMenu();
        for (Menu menu1 : menu) {
            System.out.println(menu1);
        }
    }

    @Test
    public void banner() {
        List<Banner> list = (List<Banner>) bannerService.selectAll(1, 5);
        for (Banner banner : list) {
            System.out.println(banner);
        }
    }
}