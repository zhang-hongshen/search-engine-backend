package com.zhanghongshen.soo.dao;

import com.zhanghongshen.soo.entity.IndexFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * @author Zhang Hongshen
 * @date 2021/5/25
 */
public interface IndexFileDao extends JpaRepository<IndexFile,String>{

    @Query(name = "findByFilePath",nativeQuery = true,value = "select * from index_file")
    List<IndexFile> findAll();
}