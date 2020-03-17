package com.xblog.service.impl;

import com.xblog.dao.AdminMapper;
import com.xblog.model.Admin;
import com.xblog.service.AdminService;
import com.xblog.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class AdminServiceImpl extends AbstractService<Admin> implements AdminService {
    @Resource
    private AdminMapper adminMapper;

}
