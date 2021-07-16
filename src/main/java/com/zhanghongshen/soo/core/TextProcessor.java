package com.zhanghongshen.soo.core;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.qianxinyao.analysis.jieba.Keyword;
import com.qianxinyao.analysis.jieba.TFIDFAnalyzer;
import com.zhanghongshen.soo.utils.FileUtils;
import org.jsoup.Jsoup;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Zhang Hongshen
 * @description TextPreprocess
 * @date 2021/5/3
 */
public class TextProcessor {
    private final String anySpaceRegex = "\\s+";
    private final String DEFAULT_STOP_WORDS = "src/main/resources/stop_words.txt";
    public TextProcessor(){}

    private List<String> cnProcess(String htmlStr){
        //删除html标记
        String text = Jsoup.parse(htmlStr).text();
        //中文分词
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> tokens = segmenter.process(text, JiebaSegmenter.SegMode.INDEX);
        List<String> words = new ArrayList<>();
        for(SegToken token : tokens){
            words.add(token.word);
        }
        //过滤空白词
        words.removeIf(e -> e.length() == 0 || e.matches(anySpaceRegex));
        //读取中文停用词
        List<String> chineseStopWords = FileUtils.readFileToList(new File(DEFAULT_STOP_WORDS),StandardCharsets.UTF_8);
        //删除中文停用词
        words.removeAll(chineseStopWords);
        return words;
    }

    private List<String> enProcess(String htmlStr){
        //转换为小写字母
        htmlStr = htmlStr.toLowerCase();
        //删除html标记
        String text = Jsoup.parse(htmlStr).text();
        //英文分词
        List<String> words = new ArrayList<>(Arrays.asList(text.split(" ")));
        //过滤空白词
        words.removeIf(e -> e.length() == 0 || e.matches(anySpaceRegex));
        //读取英文停用词
        List<String> englishStopWords = FileUtils.readFileToList(new File(DEFAULT_STOP_WORDS),StandardCharsets.UTF_8);
        //删除英文停用词
        words.removeAll(englishStopWords);
        //Porter Stemming方法提取词干
        Stemmer s = new Stemmer();
        List<String> res =  new ArrayList<>();
        for(String word : words){
            s.add(word);
            s.stem();
            //储存Porter-Stemming结果
            res.add(s.toString());
        }
        words.clear();
        words.addAll(res);
        return words;
    }

    public List<String> process(String text,String language){
        List<String> words = new ArrayList<>();
        HashMap<String, Method> methodMapper = new HashMap<>(2);
        try {
            methodMapper.put("chinese", TextProcessor.class.getDeclaredMethod("cnProcess", String.class));
            methodMapper.put("english", TextProcessor.class.getDeclaredMethod("enProcess", String.class));
            Method method = methodMapper.get(language.toLowerCase());
            words.addAll((List<String>) method.invoke(new TextProcessor(),text));
            return words;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }

    public void process(File originalFile, File targetFile,String language){
        try {
            if(!originalFile.isFile()){
                throw new IOException("OriginalFile must be a file.");
            }
            if(!targetFile.isFile()){
                targetFile.createNewFile();
            }
            //读取文件
            String text = FileUtils.readFileToString(originalFile,StandardCharsets.UTF_8);
            //处理
            List<String> words = process(text,language);
            //结果写入文件
            FileUtils.writeListToFile(new File(targetFile.getCanonicalPath()),words,StandardCharsets.UTF_8);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private double getVectorLength(Vector<Integer> vector){
        int length = 0;
        int n = vector.size();
        for(int i = 0; i < n; i++){
            int e = vector.elementAt(i);
            length += e * e;
        }
        return Math.sqrt(length);
    }

    public double getCosDistance(String s1,String s2,String language){
        Objects.requireNonNull(s1);
        Objects.requireNonNull(s2);
        TFIDFAnalyzer analyzer = new TFIDFAnalyzer();
        List<Keyword> keywords = analyzer.analyze(s1,10);
        Vector<Double> vector1 = new Vector<>();
        for(Keyword keyword : keywords){
            vector1.add(keyword.getTfidfValue());
        }
        keywords = analyzer.analyze(s2,10);
        Vector<Double> vector2 = new Vector<>();
        for(Keyword keyword : keywords){
            vector2.add(keyword.getTfidfValue());
        }
        double cosD = 0;
        if(vector1.size() == vector2.size()){
            int n = vector1.size();
            double l1 = 0,l2 = 0;
            for(int i = 0; i < n; i++){
                double e1 = vector1.elementAt(i);
                double e2 = vector2.elementAt(i);
                cosD += e1 * e2;
                l1 += e1 * e1;
                l2 += e2 * e2;
            }
            cosD /= (Math.sqrt(l1) * Math.sqrt(l2));
        }
        return cosD;
    }
}

