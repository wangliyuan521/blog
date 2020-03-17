package com.xblog.service.impl;

import com.xblog.dao.CommentMapper;
import com.xblog.model.Comment;
import com.xblog.service.CommentService;
import com.xblog.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class CommentServiceImpl extends AbstractService<Comment> implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findAllByBlogId(Integer blogId) {
        Condition condition = new Condition(Comment.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("blogId", blogId);
        return commentMapper.selectByCondition(condition);
    }
}
