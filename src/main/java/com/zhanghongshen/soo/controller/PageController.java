package com.zhanghongshen.soo.controller;

import com.alibaba.fastjson.JSONArray;
import com.zhanghongshen.soo.entity.Page;
import com.zhanghongshen.soo.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/25
 */

@RestController
public class PageController {
    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public JSONArray getSearchResult(@RequestParam Map<String,String> params) {
        JSONArray jsonArray = new JSONArray();
        List<Page> pages = pageService.query(params.get("searchContent"));
        for(Page page : pages){
            jsonArray.add(pageService.pageToJSONObject(page));
        }
        return jsonArray;
    }

}
