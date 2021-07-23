package com.zhanghongshen.soo.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Zhang Hongshen
 * @date 2021/5/25
 */
@Data
@TableName("page")
public class Page {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("url")
    private String url;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("keywords")
    private String keywords;

    @TableField("local_filepath")
    private String localFilepath;

    @TableField("processed_filepath")
    private String processedFilepath;

    public Page() {

    }

    public Page(String url, String title, String description, String keywords, String localFilepath) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.localFilepath = localFilepath;
    }

}
