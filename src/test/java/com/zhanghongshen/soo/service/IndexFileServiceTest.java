package com.zhanghongshen.soo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class IndexFileServiceTest {
    @Autowired
    private IndexFileService indexFileService;

    @Test
    void createIndex() {
        indexFileService.createIndex(new File("f:/resources_processed/english"),new File("f:/resources_index/english"));
    }
}