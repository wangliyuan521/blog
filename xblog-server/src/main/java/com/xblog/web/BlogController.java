package com.xblog.web;

import com.xblog.core.Result;
import com.xblog.core.ResultGenerator;
import com.xblog.model.Blog;
import com.xblog.model.BlogContent;
import com.xblog.model.BlogTag;
import com.xblog.model.BlogType;
import com.xblog.model.vo.BlogVO;
import com.xblog.service.BlogContentService;
import com.xblog.service.BlogService;
import com.xblog.service.BlogTagService;
import com.xblog.service.BlogTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Value("${blog.about.id}")
    private Integer ABOUT_ID;

    @Resource
    private BlogService blogService;

    @Resource
    private BlogContentService blogContentService;

    @Resource
    private BlogTagService blogTagService;

    @Resource
    private BlogTypeService blogTypeService;


    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Blog blog = blogService.findById(id);
        BlogContent blogContent = blogContentService.findById(id);
        BlogVO blogVO = new BlogVO();
        BeanUtils.copyProperties(blog, blogVO);
        blogVO.setContent(blogContent.getContent());

        // 设置博客标签
        List<BlogTag> tags = blogTagService.findByIds(blog.getTag());
        StringBuilder sb = new StringBuilder();
        for (BlogTag tag : tags) {
            sb.append(tag.getName() + ",");
        }
        String tagStr = sb.toString();
        blogVO.setTag(tagStr.substring(0, tagStr.length() - 1));

        // 设置博客类型
        BlogType blogType = blogTypeService.findById(blog.getType());
        blogVO.setTypeName(blogType.getName());

        return ResultGenerator.genSuccessResult(blogVO);
    }


    @GetMapping("/about")
    public Result about() {
        return detail(ABOUT_ID);
    }

    @GetMapping("/search")
    public Result search(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String keyword) {
        PageHelper.startPage(page, size);
        List<Blog> list = blogService.findByKeyword(keyword);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size, "create_date desc");
        List<Blog> list = blogService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/list/tag")
    public Result listByTag(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, Integer tagId) {
        PageHelper.startPage(page, size, "create_date desc");
        List<Blog> list = blogService.findAllByTagId(tagId);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/list/click")
    public Result listByClick(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size, "click_count desc");
        List<Blog> list = blogService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/list/comment")
    public Result listByComment(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size, "comment_count desc");
        List<Blog> list = blogService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/list/like")
    public Result listByLike(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size, "like_count desc");
        List<Blog> list = blogService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/click/add")
    public Result addClick(Integer blogId) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) return ResultGenerator.genFailResult("不存在id为" + blogId + "的博客！");
        blog.setClickCount(blog.getClickCount() + 1);
        blogService.update(blog);
        return ResultGenerator.genSuccessResult("增加点击数成功！");
    }

    @PostMapping("/like/add")
    public Result addLick(Integer blogId) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) return ResultGenerator.genFailResult("不存在id为" + blogId + "的博客！");
        blog.setLikeCount(blog.getLikeCount() + 1);
        blogService.update(blog);
        return ResultGenerator.genSuccessResult("增加喜欢数成功！");
    }

    @PostMapping("/comment/add")
    public Result addCommentCount(Integer blogId) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) return ResultGenerator.genFailResult("不存在id为" + blogId + "的博客！");
        blog.setCommentCount(blog.getCommentCount() + 1);
        blogService.update(blog);
        return ResultGenerator.genSuccessResult("增加评论数成功！");
    }
}
