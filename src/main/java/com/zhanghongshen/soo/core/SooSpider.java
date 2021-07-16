package com.zhanghongshen.soo.core;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * @author Zhang Hongshen
 * @date 2021/5/8
 */
public class SooSpider {
    /**
    * maxNumOfWebsite : max number of website to be crawled
    * rootUrl : the urls which the crawl begin
    */
    private int maxNumOfWebsite;
    private HashSet<String> rootUrls;
    /**
     * result : save the result
     */
    private List<Map<String,String>> result;
    private Collection<String> resultUrl;
    private final String govWebsiteRegex = ".gov.cn";
    public SooSpider(){
        this.maxNumOfWebsite = 100;
        this.rootUrls = new HashSet<>();
        this.result = new LinkedList<>();
        this.resultUrl = new HashSet<>();
    }

    public SooSpider(String rootUrl){
        this();
        Objects.requireNonNull(rootUrl,"Root url must not be null.");
        this.rootUrls.add(rootUrl);
    }

    public SooSpider(Collection<String> rootUrls){
        this();
        Objects.requireNonNull(rootUrls,"Root urls must not be null.");
        if(rootUrls.isEmpty()){
            throw new IllegalArgumentException("Root urls must not be empty.");
        }
        this.rootUrls.addAll(rootUrls);
    }

    public SooSpider(String rootUrl, int maxNumOfWebsite){
        this(rootUrl);
        if(maxNumOfWebsite < 0){
            throw new IllegalArgumentException("The max num of website must be negative.");
        }
        this.maxNumOfWebsite = maxNumOfWebsite;
    }

    public SooSpider(Collection<String> rootUrls, int maxNumOfWebsite){
        this(rootUrls);
        if(maxNumOfWebsite < 0){
            throw new IllegalArgumentException("The max num of website must be negative.");
        }
        this.maxNumOfWebsite = maxNumOfWebsite;
    }

    public void setMaxNumOfWebsite(int maxNumOfWebsite) {
        this.maxNumOfWebsite = maxNumOfWebsite;
    }

    public void setRootUrl(Collection<String> rootUrls) {
        this.rootUrls = new HashSet<>(rootUrls);
    }

    public List<String> getResultUrl(){
        return new ArrayList<>(resultUrl);
    }

    public int getMaxNumOfWebsite() {
        return maxNumOfWebsite;
    }

    public List<Map<String, String>> getResult() {
        return result;
    }

    /**
     * @description check whether a url is valid
     * @param url url to be checked
     * @return true --  url is a valid url
     *         false -- url isn't a valid url
     */
    private boolean isValidUrl(String url){
        return !(url.isEmpty() || url.contains(govWebsiteRegex));
    }

    public void start(){
        LinkedList<String> urls = new LinkedList<>(rootUrls);
        while (resultUrl.size() < maxNumOfWebsite) {
            String url = urls.removeFirst();
            try{
                if(isValidUrl(url) && !resultUrl.contains(url)){
                    Connection connection = Jsoup.connect(url);
                    //伪造请求头
                    connection.header("User-Agent", UserAgent.random());
                    Document doc = connection.get();
                    resultUrl.add(url);
                    //解析网页
                    Map<String,String> info = new HashMap<>(5);
                    info.put("url",url);
                    info.put("title",doc.head().select("title").text());
                    info.put("description",doc.head().select("meta[name=description]").attr("content"));
                    info.put("keywords",doc.head().select("meta[name=keywords]").attr("content"));
                    info.put("content",doc.toString());
                    result.add(info);
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
