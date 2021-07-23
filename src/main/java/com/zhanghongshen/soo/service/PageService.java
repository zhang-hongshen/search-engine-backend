package com.zhanghongshen.soo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhanghongshen.soo.core.Querier;
import com.zhanghongshen.soo.dao.IndexFileDao;
import com.zhanghongshen.soo.dao.PageDao;
import com.zhanghongshen.soo.pojo.entity.IndexFile;
import com.zhanghongshen.soo.pojo.entity.Page;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/25
 */
@Service
public class PageService {
    @Autowired
    private PageDao pageDao;
    @Autowired
    private IndexFileDao indexFileDao;

    /**
     * 查询
     * @param query 查询语句
     * @return
     */
    public List<Page> query(String query,int page,int limit){
        List<IndexFile> indexFiles =indexFileDao.selectList(null);
        Map<Document,Float> documents = new HashMap<>();
        for(IndexFile indexfile : indexFiles){
            documents.putAll(Querier.query(new File(indexfile.getFilepath()),query));
        }
        List<Map.Entry<Document,Float>> sortedDocuments =  new ArrayList<>(documents.entrySet());
        sortedDocuments.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        QueryWrapper<Page> queryWrapper = new QueryWrapper<>();
        Set<String> processedFilePaths = new LinkedHashSet<>();
        for(Map.Entry<Document,Float> sortedDocument : sortedDocuments){
            Document doc = sortedDocument.getKey();
            processedFilePaths.add(doc.get("filePath"));
        }
        queryWrapper.in("processed_filepath",processedFilePaths);
        IPage<Page> pageIPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page,limit);
        pageDao.selectPage(pageIPage,queryWrapper);
        return pageIPage.getRecords();
    }

}
