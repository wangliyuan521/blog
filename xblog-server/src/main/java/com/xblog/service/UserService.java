package com.xblog.service;

import com.xblog.core.Service;
import com.xblog.model.User;

import java.util.List;

public interface UserService extends Service<User> {
    User getByUsernameAndPassword(String username, String password);

    List<User> getUserByPage(Integer page, Integer limit);
}
