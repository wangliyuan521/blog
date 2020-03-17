package com.xblog.service.impl;

import com.xblog.dao.LinkMapper;
import com.xblog.model.Link;
import com.xblog.service.LinkService;
import com.xblog.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class LinkServiceImpl extends AbstractService<Link> implements LinkService {
    @Resource
    private LinkMapper linkMapper;

}
