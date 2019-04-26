package com.baizhi.controller;

import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("detail")
public class WenController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("wen")
    public Object QueryWen(String id, String uid) {
        if (uid == null || uid == null) {
            return new Error("参数不可为空");
        } else {
            Map map = new HashMap();
            map.put("album", albumService.selectAlbumByid(id));
            return map;
        }
    }

}
