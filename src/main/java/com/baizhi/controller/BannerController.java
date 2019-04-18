package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("selectAll")
    @ResponseBody
    public Map select(int page, int rows) {
        Map map = bannerService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("addbanner")
    @ResponseBody
    public Map addbanner(MultipartFile file, Banner banner) throws Exception {
        Map map = new HashMap();

        //1.获取源文件夹
        String oldName = file.getOriginalFilename();
        //2.将接收的文件复制到服务器上
        String uuid = UUID.randomUUID().toString();
//        System.out.println("action 文件名"+oldName);
        String newname = uuid + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File("D:\\IdealCode2\\cmfz\\src\\main\\webapp\\img\\shouye\\" + newname));
        banner.setImg_path(newname);
        Date date = new Date();
        banner.setCreat_date(date);
        banner.setStatus(1);
        try {
            bannerService.insert(banner);
            System.out.println();
            map.put("isadd", true);
        } catch (Exception e) {
            map.put("isadd", false);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("deleteBanner")
    @ResponseBody
    public void deleteBanner(int id) {
        bannerService.delete(id);
    }

    @RequestMapping("updateBanner")
    @ResponseBody
    public void updateBanner(Banner banner) {
        bannerService.update(banner);
    }
}
