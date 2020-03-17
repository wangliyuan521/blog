package com.xblog.service.impl;

import com.xblog.core.AbstractService;
import com.xblog.dao.UserMapper;
import com.xblog.model.User;
import com.xblog.service.UserService;
import com.xblog.util.EmptyUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userMapper.selectOne(user);
    }

    @Override
    public List<User> getUserByPage(Integer page, Integer limit) {
        if (EmptyUtil.isEmpty(page) || EmptyUtil.isEmpty(limit)){
            return userMapper.selectAll();
        }
        page = (page - 1) * limit;
        return userMapper.selectByLimit(page, limit);
    }
}
