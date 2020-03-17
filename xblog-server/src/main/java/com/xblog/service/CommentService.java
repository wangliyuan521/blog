package com.xblog.service;
import com.xblog.model.Comment;
import com.xblog.core.Service;

import java.util.List;


public interface CommentService extends Service<Comment> {

    List<Comment> findAllByBlogId(Integer blogId);
}
