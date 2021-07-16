package com.zhanghongshen.soo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class TextProcessorServiceTest {
    @Autowired
    TextProcessorService textProcessorService;

    @Test
    void process(){
        textProcessorService.process(new File("f:/resources_org/chinese/148.html"),new File("f:/resources_processed/chinese/148.html"),"chinese");
    }

    @Test
    void processAll() {
        textProcessorService.processAll(new File("f:/resources_org/english"),new File("f:/resources_processed/english"),"english");
    }

}