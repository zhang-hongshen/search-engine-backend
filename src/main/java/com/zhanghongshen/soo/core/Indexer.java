package com.zhanghongshen.soo.core;

import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.zhanghongshen.soo.core.jiebaforlucene.JiebaAnalyzer;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/19
 */
public class Indexer {
    public static void create(File originalFile, File indexFile){
        try {
            Directory directory = FSDirectory.open(indexFile.toPath());
            Analyzer analyzer = new JiebaAnalyzer(SegMode.INDEX);
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory,config);
            for(File file : Objects.requireNonNull(originalFile.listFiles())){
                Document doc = new Document();
                String filePath = file.getPath();
                String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                doc.add(new StoredField("filePath",filePath));
                doc.add(new TextField("fileContent",fileContent, Store.YES));
                indexWriter.addDocument(doc);
            }
            indexWriter.close();
            analyzer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
