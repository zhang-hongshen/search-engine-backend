package com.zhanghongshen.soo.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;

/**
 * @author Zhang Hongshen
 * @date 2021/5/25
 */
@Entity
@Data
@Table(name = "page")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "url")
    @NotNull
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "local_filepath")
    private String filepath;

    public Page() {

    }

    public Page(String url, String title, String description, String keywords, String filepath) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.filepath = filepath;
    }

}
