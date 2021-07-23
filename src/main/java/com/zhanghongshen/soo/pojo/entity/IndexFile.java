package com.zhanghongshen.soo.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Zhang Hongshen
 * @date 2021/5/27
 */
@Data
@TableName("index_file")
public class IndexFile {
    @TableId(value = "filepath")
    private String filepath;

    public IndexFile() {
    }

    public IndexFile(String filepath) {
        this.filepath = filepath;
    }
}
