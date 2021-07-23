package com.zhanghongshen.soo.controller;

import com.zhanghongshen.soo.pojo.VO.PageVO;
import com.zhanghongshen.soo.pojo.entity.Page;
import com.zhanghongshen.soo.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Zhang Hongshen
 * @description
 * @date 2021/5/25
 */

@RestController
@RequestMapping("/page")
public class PageController {
    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResultBean<List<PageVO>> getSearchResult(@RequestParam Map<String,String> params) {
        System.out.println(params);
        String searchContent = params.get("searchContent");
        int pageIndex = Integer.parseInt(params.get("pageIndex"));
        int pageSize = Integer.parseInt(params.get("pageSize"));
        List<Page> pages = pageService.query(searchContent,pageIndex,pageSize);
        List<PageVO> data = new ArrayList<>();
        for(Page page : pages){
            data.add(new PageVO(page.getUrl(),page.getTitle(),page.getDescription()));
        }
        return new ResultBean<>(data);
    }
}
