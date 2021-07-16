package com.zhanghongshen.soo.core.jiebaforlucene;

import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.WordDictionary;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/21
 */
public class JiebaAnalyzer extends Analyzer {
    private static final String DEFAULT_STOP_WORDS_FILE = "src/main/resources/stop_words.txt";
    private final CharArraySet stopWords;
    private final SegMode segMode;

    public JiebaAnalyzer(SegMode segMode){
        this.segMode = segMode;
        this.stopWords = new CharArraySet(128, true);
    }

    public JiebaAnalyzer(SegMode segMode, CharArraySet stopWords){
        this.segMode = segMode;
        this.stopWords = new CharArraySet(stopWords, true);
    }

    /**
     * use for add user dictionary and stop words,
     * user dictionary need with .dict suffix, stop words with file name: stopwords.txt
     * @param userDictPath
     */
    public void init(String userDictPath){
        if (!StringUtils.isEmpty(userDictPath)){
            File file  = new File(userDictPath);
            if (file.exists()){
                //load user dict
                WordDictionary wordDictionary = WordDictionary.getInstance();
                wordDictionary.init(Paths.get(userDictPath));

                //load stop words from userDictPath with name stopwords.txt, one word per line.
                loadStopWords(Paths.get(userDictPath, DEFAULT_STOP_WORDS_FILE) , StandardCharsets.UTF_8);
            }
        }
    }

    /**
     * load stop words from path
     * @param userDict stop word path, one word per line
     * @param charset
     */
    private void loadStopWords(Path userDict, Charset charset) {
        try {
            BufferedReader br = Files.newBufferedReader(userDict, charset);
            int count = 0;
            while (br.ready()) {
                String line = br.readLine();
                if (! StringUtils.isEmpty(line)){
                    stopWords.add(line);
                    ++count;
                }
            }
            System.out.printf(Locale.getDefault(), "%s: load stop words total:%d!%n", userDict, count);
            br.close();
        }
        catch (IOException e) {
            System.err.printf(Locale.getDefault(), "%s: load stop words failure!%n", userDict);
        }
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer tokenizer = new JiebaTokenizer(segMode);
        TokenStream result = tokenizer;
        if (!stopWords.isEmpty()) {
            result = new StopFilter(result, stopWords);
        }
        return new TokenStreamComponents(tokenizer, result);
    }
}