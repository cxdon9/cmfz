package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.ChapterDao;
import com.baizhi.service.AlbumService;
import com.baizhi.util.AudioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterDao chapterDao;


    @RequestMapping("getAlbum")
    @ResponseBody
    public List<Album> getAlbum() {
//        System.out.println(albumService.getAlbum());
        return albumService.getAlbum();
    }

    @RequestMapping("selectAlbumByid")
    @ResponseBody
    public Album selectAlbumByid(String id) {
        return albumService.selectAlbumByid(id);
    }


    @RequestMapping("addalbum")
    @ResponseBody
    public Map addalbum(MultipartFile file, Album album) throws IOException {
        Map map = new HashMap<>();
        String oldName = file.getOriginalFilename();
        //2.将接收的文件复制到服务器上
        String uuid = UUID.randomUUID().toString();
//        System.out.println("action 文件名"+oldName);
        String newname = uuid + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File("D:\\IdealCode2\\cmfz\\src\\main\\webapp\\img\\audioCollection\\" + newname));
        album.setId(uuid);
        album.setImg_path(newname);
        Date date = new Date();
        album.setPublish_date(date);
        try {
            albumService.insert(album);
            System.out.println();
            map.put("isadd", true);
        } catch (Exception e) {
            map.put("isadd", false);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("selectAllAlbum")
    @ResponseBody
    public List<Album> selectAllAlbum() {
        return albumService.selectAll();
    }

    @RequestMapping("addChapter")
    @ResponseBody
    public Map addChapter(MultipartFile file, Chapter chapter) throws IOException {
        Map map = new HashMap<>();
        String oldName = file.getOriginalFilename();
        //2.将接收的文件复制到服务器上
        String uuid = UUID.randomUUID().toString();
//        System.out.println("action 文件名"+oldName);
        String newname = uuid + oldName.substring(oldName.lastIndexOf("."));
        File file11 = new File("D:\\IdealCode2\\cmfz\\src\\main\\webapp\\img\\audioCollection\\" + newname);
        file.transferTo(file11);
        chapter.setTitle(oldName);
        //过去文件时长
        Long duration = AudioUtil.getDuration(file11);
        chapter.setDuration(Math.toIntExact(duration));
        Date date = new Date();
        chapter.setPublish_date(date);
        chapter.setDown_load_path("D:\\IdealCode2\\cmfz\\src\\main\\webapp\\img\\audioCollection\\" + newname);
        chapter.setSize(getPrintSize(file.getSize()));
        try {
            chapterDao.insert(chapter);
            System.out.println();
            map.put("isadd", true);
        } catch (Exception e) {
            map.put("isadd", false);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("download")
    @ResponseBody
    public void download(int id, HttpServletResponse resp) throws IOException {
        Chapter chapter = chapterDao.selectByPrimaryKey(id);
        String down_load_path = chapter.getDown_load_path();
        InputStream is = new FileInputStream(down_load_path);
        //设置响应头 attachment  附件
        String s1 = URLEncoder.encode(chapter.getTitle(), "UTF-8");
        resp.setHeader("content-disposition", "attachment;filename=" + s1);
        OutputStream os = resp.getOutputStream();

        while (true) {
            int i = is.read();
            if (i == -1) {
                break;
            }
            os.write(i);
        }
    }

    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

}
