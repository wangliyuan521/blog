package com.xblog.service.impl;

import com.xblog.core.AbstractService;
import com.xblog.dao.BlogTypeMapper;
import com.xblog.model.BlogType;
import com.xblog.service.BlogTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class BlogTypeServiceImpl extends AbstractService<BlogType> implements BlogTypeService {
    @Resource
    private BlogTypeMapper typeNameMapper;

}
