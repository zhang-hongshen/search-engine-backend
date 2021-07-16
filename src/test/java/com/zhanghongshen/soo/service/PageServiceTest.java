package com.zhanghongshen.soo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PageServiceTest {
    @Autowired
    private PageService pageService;

    @Test
    void query() {
        System.out.println(pageService.query("百度"));
    }
}