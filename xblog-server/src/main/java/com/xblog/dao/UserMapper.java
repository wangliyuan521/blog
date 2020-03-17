package com.xblog.dao;

import com.xblog.core.Mapper;
import com.xblog.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    List<User> selectByLimit(@Param("page") Integer page, @Param("limit") Integer limit);
}
