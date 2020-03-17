package com.xblog.web;
import com.xblog.core.Result;
import com.xblog.core.ResultGenerator;
import com.xblog.model.Link;
import com.xblog.service.LinkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    private LinkService linkService;


    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Link link = linkService.findById(id);
        return ResultGenerator.genSuccessResult(link);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size, "link_order asc");
        List<Link> list = linkService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
