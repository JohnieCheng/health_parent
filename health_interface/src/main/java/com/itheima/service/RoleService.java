package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;

import java.util.List;

public interface RoleService {
    Role findById(Integer id);

    PageResult pageQuery(QueryPageBean queryPageBean);

    List<Role> findAll();
}
