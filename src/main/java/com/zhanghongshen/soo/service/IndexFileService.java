package com.zhanghongshen.soo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.zhanghongshen.soo.utils.FileUtils;
import com.zhanghongshen.soo.core.jiebaforlucene.JiebaAnalyzer;
import com.zhanghongshen.soo.dao.IndexFileDao;
import com.zhanghongshen.soo.dao.PageDao;
import com.zhanghongshen.soo.pojo.entity.IndexFile;
import com.zhanghongshen.soo.pojo.entity.Page;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/25
 */
@Service
public class IndexFileService {
    @Autowired
    private IndexFileDao indexFileDao;
    @Autowired
    private PageDao pageDao;

    /**
     *
     * @param originalFile 源文件所在目录
     * @param indexFile 索引文件所在目录
     */
    public void createIndex(@NonNull File originalFile, @NonNull File indexFile){
        Objects.requireNonNull(originalFile,"File must not be null.");
        Objects.requireNonNull(indexFile,"File must not be null.");
        try {
            Directory directory = FSDirectory.open(indexFile.toPath());
            Analyzer analyzer = new JiebaAnalyzer(SegMode.INDEX);
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory,config);
            for(File file : Objects.requireNonNull(originalFile.listFiles())){
                String filePath = file.getCanonicalPath();
                QueryWrapper<Page> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("processed_filepath",filePath);
                Page page = pageDao.selectOne(queryWrapper);
                Document doc = new Document();
                String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                doc.add(new TextField("title",page.getTitle(), Store.YES));
                doc.add(new TextField("keywords",page.getKeywords(), Store.YES));
                doc.add(new TextField("description",page.getDescription(), Store.YES));
                doc.add(new StoredField("filePath",filePath));
                doc.add(new TextField("fileContent",fileContent, Store.YES));
                indexWriter.addDocument(doc);
            }
            indexWriter.close();
            analyzer.close();
            indexFileDao.insert(new IndexFile(indexFile.getCanonicalPath()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
