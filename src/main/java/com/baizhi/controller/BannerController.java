package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerDao;
import com.baizhi.service.BannerService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;

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

    @RequestMapping("exportXsl")
    public void exportXsl(HttpServletResponse response) {
        List<Banner> list = bannerDao.selectAll();
        //创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建日期格式
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd日");
        //创建日期格式的样式
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setDataFormat(format);
        //创建工作表
        Sheet sheet = workbook.createSheet("banner");

        //id=1, title=111, img_path=, creat_date=, status=1)
        String[] strings = {"ID", "标题", "图片路径", "创建日期", "状态(1:展示;2:不展示)"};
        //创建行
        Row row = sheet.createRow(0);
        for (int i = 0; i < strings.length; i++) {
            //设置宽度
            sheet.setColumnWidth(i + 1, 20 * 256);
            //创建单元格
            Cell cell = row.createCell(i);
            //单元格赋值
            cell.setCellValue(strings[i]);
        }
        for (int i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(list.get(i).getId());
            row1.createCell(1).setCellValue(list.get(i).getTitle());
            row1.createCell(2).setCellValue(list.get(i).getImg_path());
            Cell cell = row1.createCell(3);
            cell.setCellStyle(cellStyle1);
            cell.setCellValue(list.get(i).getCreat_date());
            row1.createCell(4).setCellValue(list.get(i).getStatus());
        }
        String oldName = "轮播图.xlsx";
        String encode = null;
        try {
            encode = URLEncoder.encode(oldName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应头
        response.setHeader("Content-Disposition", "attachment;fileName=" + encode);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //直接将写入到输出流中即可;
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
