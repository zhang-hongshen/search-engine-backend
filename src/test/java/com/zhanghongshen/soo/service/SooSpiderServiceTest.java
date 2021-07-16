package com.zhanghongshen.soo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Arrays;

@SpringBootTest
class SooSpiderServiceTest {
    @Autowired
    private SooSpiderService service;
    String[] chineseUrls = {
            "https://www.baidu.com/",
            "https://www.bookmarkearth.com/"
    };
    String[] englishUrls = {
            "https://docs.oracle.com/en/java/javase/11/docs/api/",
            "https://docs.oracle.com/javase/8/docs/api/"
    };
    @Test
    void start() {

        long startTime =  System.currentTimeMillis();
        service.start(Arrays.asList(englishUrls),500,new File("f:/resources_org/english"));
        long endTime =  System.currentTimeMillis();
        System.out.println("总耗时："+ (endTime - startTime) / 1000.0 + "s");
    }
}