package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Set<Role> findByUserId(Integer userId);

    Role findById(Integer id);

    Page<User> selectByCondition(String queryString);

    List<Role> findAll();
}
