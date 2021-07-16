package com.zhanghongshen.soo.service;

import com.zhanghongshen.soo.utils.FileUtils;
import com.zhanghongshen.soo.core.TextProcessor;
import com.zhanghongshen.soo.dao.PageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/27
 */

@Service
public class TextProcessorService {
    @Autowired
    private PageDao pageDao;
    private final TextProcessor processor = new TextProcessor();

    public void process(File originalFile, File targetFile, String language){
        processor.process(originalFile,targetFile,language);
        pageDao.updateByFilePath(targetFile.getAbsolutePath(),originalFile.getAbsolutePath());
    }

    public void processAll(File originalFile, File targetFile, String language){

        try {
            if (!originalFile.exists()){
                throw new IOException("originalFile doesn;t exist.");
            }
            if(!originalFile.isDirectory()){
                throw new IOException("originalFile must be a directory.");
            }
            if(!targetFile.exists()){
                targetFile.mkdir();
            }
            if(!targetFile.isDirectory()){
                throw new IOException("targetFile must be a directory.");
            }
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
        for(File file : Objects.requireNonNull(originalFile.listFiles())){
            String filePath = FileUtils.randomFileName(targetFile,".html");;
            process(file,new File(filePath),language);
        }
    }
}
