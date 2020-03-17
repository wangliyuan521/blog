package com.xblog.service.impl;

import com.xblog.core.AbstractService;
import com.xblog.dao.BlogTagMapper;
import com.xblog.model.BlogTag;
import com.xblog.service.BlogTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class BlogTagServiceImpl extends AbstractService<BlogTag> implements BlogTagService {
    @Resource
    private BlogTagMapper tagNameMapper;

}
