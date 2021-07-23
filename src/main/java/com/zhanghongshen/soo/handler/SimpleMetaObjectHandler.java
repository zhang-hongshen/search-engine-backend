package com.zhanghongshen.soo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.text.SimpleDateFormat;

@Slf4j
@Component
public class SimpleMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //this.strictInsertFill(metaObject, "createTime", Timestamp.class, Timestamp.valueOf(simpleDate.format(new Date())));
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        this.strictUpdateFill(metaObject, "updateTime", Timestamp.class, Timestamp.valueOf(simpleDate.format(new Date())));
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }

}
