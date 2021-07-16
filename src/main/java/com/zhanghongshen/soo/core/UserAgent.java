package com.zhanghongshen.soo.core;

import java.util.Random;

/**
 * @author Zhang Hongshen
 * @description Create random User-Agent
 * @date 2021/5/13
 */
public class UserAgent {
    private static final String[] CHROME = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11"
    };
    private static final String[] EDGE = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.56"
    };
    private static final String[] FIREFOX = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1"
    };
    private static final String[] SAFARI = {
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50"
    };
    private static final String[] IE = {
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0",
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)"
    };
    private static final String[] MAXTHON = {
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)"
    };
    private static final String[] SOUGOU = {
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)"
    };
    private static final String[] AVANT = {
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Avant Browser)"
    };
    private static final String[] GREEN = {
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"
    };
    private static final String[][] BROWSERS = {CHROME,EDGE,FIREFOX,SAFARI,IE,MAXTHON,SOUGOU,AVANT,GREEN};

    public static String random(){
        Random rand = new Random();
        String[] browser = BROWSERS[rand.nextInt(BROWSERS.length)];
        return browser[rand.nextInt(browser.length)];
    }
}
