package com.xblog.web;
import com.xblog.core.Result;
import com.xblog.core.ResultGenerator;
import com.xblog.model.BlogTag;
import com.xblog.service.BlogTagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blog/tag")
public class BlogTagController {

    @Resource
    private BlogTagService blogTagService;

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        BlogTag blogTag = blogTagService.findById(id);
        return ResultGenerator.genSuccessResult(blogTag);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<BlogTag> list = blogTagService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
