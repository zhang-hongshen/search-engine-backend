package com.zhanghongshen.soo.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Zhang Hongshen
 * @description Some file actions
 * @date 2021/5/8
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static void writeListToFile(File file,List<String> list,Charset charset){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file.getCanonicalPath());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,charset);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            for(String s : list){
                bufferedWriter.write(s.trim());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFileToList(File file,Charset charset){
        List<String> words = new ArrayList<>();
        try {
            if(!file.exists()){
                throw new IOException("File doesn't exist.");
            }
            if(!file.isFile()){
                throw new IOException("File is not a file.");
            }
            FileInputStream fileInputStream = new FileInputStream(file.getCanonicalPath());
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                words.add(s.trim());
            }
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return words;
    }

    /**。
     * 随机生成文件夹下的文件名
     * @param file
     * @param fileType
     * @return
     */
    public static String randomFileName(File file,String fileType){
        if(!file.isDirectory()){
            throw new IllegalArgumentException("file must be a directory.");
        }
        String filePath = "";
        do {
            try {
                filePath = file.getCanonicalPath() + File.separator + UUID.randomUUID() + fileType;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (new File(filePath).exists());
        return filePath;
    }

}
