package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleDao;
import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "first", produces = "text/plain;charset=UTF-8")
public class FirstController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("queryAll")
    public Object queryAll(String uid, String type, String sub_type) {
        //type=all 轮播图  type 0：闻，1：思  （只有闻的数据才有)
        if (uid == null || type == null) {
            return "参数不可为空";
        } else {
            if (type.equals("all")) {
                Map map = new HashMap<>();
                map.put("album", albumService.getAlbum());
                map.put("header", bannerService.selectAll(1, 5));
                map.put("article", articleDao.selectAll());
                return map;
            } else if (type.equals("wen")) {
                Map<String, Object> map = new HashMap<>();
                map.put("album", albumService.getAlbum());
                return map;
            } else {
                if (sub_type == null) {
                    return "思类型为空未查到数据";
                } else {
                    if ("ssyj".equals(sub_type)) {
                        Map<String, Object> map = new HashMap<>();
                        Article article = new Article();
                        article.setMaster_id(1);
                        map.put("article", articleDao.select(article));
                        return map;
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("article", articleDao.selectAll());
                        return map;
                    }
                }
            }
        }
    }
}
