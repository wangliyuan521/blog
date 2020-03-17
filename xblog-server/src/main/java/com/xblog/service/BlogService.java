package com.xblog.service;
import com.xblog.model.Blog;
import com.xblog.core.Service;

import java.util.List;


public interface BlogService extends Service<Blog> {

    Integer saveAndGetId(Blog blog);

    List<Blog> findByKeyword(String keyword);

    List<Blog> findAllByTagId(Integer tagId);
}
