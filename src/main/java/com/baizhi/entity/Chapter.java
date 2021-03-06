package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chapter")
public class Chapter {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Excel(name = "音频编号")
    private Integer id;
    @Excel(name = "音频标题")
    private String title;
    @Excel(name = "音频大小")
    private String size;
    @Excel(name = "音频时长")
    private Integer duration; //时长
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", format = "yyyy年MM月dd日HH时mm分ss秒", width = 50)
    private Date publish_date;
    private String down_load_path;
    private String album_id;

    @Transient
    private Chapter chapter;
}
