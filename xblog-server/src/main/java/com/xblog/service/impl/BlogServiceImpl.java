package com.xblog.service.impl;

import com.xblog.dao.BlogMapper;
import com.xblog.model.Blog;
import com.xblog.service.BlogService;
import com.xblog.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class BlogServiceImpl extends AbstractService<Blog> implements BlogService {
    @Resource
    private BlogMapper blogMapper;


    @Override
    public Integer saveAndGetId(Blog blog) {
        blogMapper.insert(blog);
        return blog.getId();
    }

    @Override
    public List<Blog> findByKeyword(String keyword) {
        Condition condition = new Condition(Blog.class);
        Example.Criteria criteria = condition.createCriteria();
        Example.Criteria criteria1 = condition.createCriteria();
        if (StringUtils.isNotBlank(keyword)) {
            criteria.andLike("title", "%" + keyword + "%");
            criteria1.andLike("summary", "%" + keyword + "%");
            condition.or(criteria1);
        }
        return blogMapper.selectByCondition(condition);
    }

    @Override
    public List<Blog> findAllByTagId(Integer tagId) {
        Condition condition = new Condition(Blog.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.orLike("tag", tagId + ",%");
        criteria.orLike("tag", "%," + tagId + ",%");
        criteria.orLike("tag", "%," + tagId);
        criteria.orLike("tag", tagId.toString());
        return blogMapper.selectByCondition(condition);
    }
}
