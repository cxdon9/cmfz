package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "album")
public class Album {
    @Id
    @Excel(name = "编号", needMerge = true)
    private String id;
    @Excel(name = "标题", needMerge = true)
    private String title;
    @Excel(name = "集数", needMerge = true)
    private Integer amount; //集数
    @Excel(name = "封面", type = 2, width = 30, height = 40, needMerge = true)
    private String img_path;
    @Excel(name = "评分", needMerge = true)
    private Double score;
    @Excel(name = "作者", needMerge = true)
    private String author;
    @Excel(name = "播音", needMerge = true)
    private String boardcast;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册日期", format = "yyyy年MM月dd日HH时mm分ss秒", width = 50, needMerge = true)
    private Date publish_date;
    @Excel(name = "简介", needMerge = true)
    private String brief; //简介

    @Transient
    @ExcelCollection(name = "章节")
    private List<Chapter> children;
}
