package com.zhanghongshen.soo.core;

import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.zhanghongshen.soo.core.jiebaforlucene.JiebaAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/19
 */
public class Querier {
    public static Map<Document,Float> query(File indexFile,String query){
        //查询Field
        String[] fields = {"title","keywords","description","fileContent"};
        //Field权重
        Map<String, Float> boosts = new HashMap<>(4){{
            put("title",1000f);
            put("keywords",50f);
            put("description",2f);
            put("fileContent",1f);
        }};
        return match(indexFile,query,fields,boosts);
    }

    public static Map<Document,Float> match(File indexFile, String query, String[] fields, Map<String, Float> boosts){
        Map<Document,Float> documents = new HashMap<>();
        try {
            Directory directory = FSDirectory.open(indexFile.toPath());
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, new JiebaAnalyzer(SegMode.SEARCH), boosts);
            Query q = queryParser.parse(query);
            TopDocs topDocs = indexSearcher.search(q,Integer.MAX_VALUE);
            for(ScoreDoc scoreDoc : topDocs.scoreDocs){
                Document doc = indexSearcher.doc(scoreDoc.doc);
                documents.put(doc,scoreDoc.score);
            }
            indexReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return documents;
    }
}
