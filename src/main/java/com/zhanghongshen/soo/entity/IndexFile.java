package com.zhanghongshen.soo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Zhang Hongshen
 * @date 2021/5/27
 */
@Entity
@Data
@Table(name = "index_file")
public class IndexFile {
    @Id
    private String filepath;

    public IndexFile() {
    }

    public IndexFile(String filepath) {
        this.filepath = filepath;
    }

}
