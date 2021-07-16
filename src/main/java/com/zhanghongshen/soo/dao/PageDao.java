package com.zhanghongshen.soo.dao;

import com.zhanghongshen.soo.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zhang Hongshen
 * @date 2021/5/25
 */
public interface PageDao extends JpaRepository<Page,String> {

    @Query(name = "findByFilePath",nativeQuery = true,value = "select * from page where processed_filepath =?1")
    Page findByProcessedFilePath(String processedFilePath);

    @Transactional
    @Modifying
    @Query(name = "updateByFilePath",nativeQuery = true,value = "update page set processed_filepath =?1 where local_filepath =?1")
    void updateByFilePath(String value,String filepath);
}
