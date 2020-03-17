package com.xblog.service.impl;

import com.xblog.core.AbstractService;
import com.xblog.dao.ProjectMapper;
import com.xblog.model.Project;
import com.xblog.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class ProjectServiceImpl extends AbstractService<Project> implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;

}
