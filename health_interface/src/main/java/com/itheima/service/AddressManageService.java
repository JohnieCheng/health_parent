package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Address;

import java.util.List;

public interface AddressManageService {
    PageResult pageQuery(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    void add(Address address);

    List<Address> findAll();

}
