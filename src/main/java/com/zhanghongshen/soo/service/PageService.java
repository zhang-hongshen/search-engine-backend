package com.zhanghongshen.soo.service;

import com.alibaba.fastjson.JSONObject;
import com.zhanghongshen.soo.core.Querier;
import com.zhanghongshen.soo.dao.IndexFileDao;
import com.zhanghongshen.soo.entity.IndexFile;
import com.zhanghongshen.soo.entity.Page;
import com.zhanghongshen.soo.dao.PageDao;
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
    public List<Page> query(String query){
        List<IndexFile> indexFiles =indexFileDao.findAll();
        Map<Document,Float> documents = new HashMap<>();
        for(IndexFile indexfile : indexFiles){
            documents.putAll(Querier.query(new File(indexfile.getFilepath()),query));
        }
        List<Map.Entry<Document,Float>> sortedDocuments =  new ArrayList<>(documents.entrySet());
        sortedDocuments.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        List<Page> result = new ArrayList<>();
        for(Map.Entry<Document,Float> sortedDocument : sortedDocuments){
            Document doc = sortedDocument.getKey();
            String filePath = doc.get("filePath");
            Page page = pageDao.findByProcessedFilePath(filePath);
            result.add(page);
        }
        return result;
    }

    /**
     * 转化为Json对象，主要发送给前端
     * @param page page类对象
     * @return Json对象
     */
    public JSONObject pageToJSONObject(Page page){
        JSONObject object = new JSONObject();
        object.put("url",page.getUrl());
        object.put("title",page.getTitle());
        object.put("description",page.getDescription());
        return object;
    }
}
