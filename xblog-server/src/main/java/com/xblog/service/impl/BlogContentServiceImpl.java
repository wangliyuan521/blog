package com.xblog.service.impl;

import com.xblog.core.AbstractService;
import com.xblog.dao.BlogContentMapper;
import com.xblog.model.BlogContent;
import com.xblog.service.BlogContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class BlogContentServiceImpl extends AbstractService<BlogContent> implements BlogContentService {
    @Resource
    private BlogContentMapper blogContentMapper;

}
