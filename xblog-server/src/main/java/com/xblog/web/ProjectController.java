package com.xblog.web;
import com.xblog.core.Result;
import com.xblog.core.ResultGenerator;
import com.xblog.model.Project;
import com.xblog.service.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;


    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Project project = projectService.findById(id);
        if (project == null) return ResultGenerator.genFailResult("不存在id为" + id + "的项目！");
        return ResultGenerator.genSuccessResult(project);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size, "project_order asc");
        List<Project> list = projectService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
