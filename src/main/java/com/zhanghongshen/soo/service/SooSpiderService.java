package com.zhanghongshen.soo.service;

import com.zhanghongshen.soo.utils.FileUtils;
import com.zhanghongshen.soo.core.UserAgent;
import com.zhanghongshen.soo.dao.PageDao;
import com.zhanghongshen.soo.entity.Page;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Zhang Hongshen
 * @description A simple webspider service
 * @date 2021/5/29
 */
@Service
public class SooSpiderService {
    @Autowired
    private PageDao pageDao;
    private final String govWebsiteRegex = ".gov.cn";

    private boolean isValidUrl(String url){
        return !(url.isEmpty() || url.contains(govWebsiteRegex));
    }

    public void start(Collection<String> rootUrls, int maxNumOfWebsite, File file){
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            if(!file.isDirectory()){
                throw new IOException("File must be a directory.");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        //待爬取的url
        LinkedList<String> urls = new LinkedList<>(rootUrls);
        //已爬取的url
        Set<String> resultUrl = new HashSet<>();
        while (resultUrl.size() < maxNumOfWebsite) {
            String url = urls.removeFirst();
            try{
                if(isValidUrl(url) && !resultUrl.contains(url)){
                    Connection connection = Jsoup.connect(url);
                    //伪造请求头
                    connection.header("User-Agent", UserAgent.random());
                    //get请求
                    Document doc = connection.get();
                    resultUrl.add(url);
                    //解析网页
                    String content = doc.toString();
                    String title = doc.head().select("title").text();
                    String description = doc.head().select("meta[name=description]").attr("content");
                    String keywords = doc.head().select("meta[name=keywords]").attr("content");
                    String filePath = FileUtils.randomFileName(file,".html");
                    //写入page表
                    pageDao.save(new Page(url,title,description,keywords,filePath));
                    //保存为本地文件
                    FileUtils.writeStringToFile(new File(filePath),content, StandardCharsets.UTF_8);
                    //抓取a标签的href属性值
                    Elements links = doc.select("a[href]");
                    for (Element link : links) {
                        //获取绝对路径
                        String linkedUrl = link.absUrl("href");
                        urls.add(linkedUrl);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
