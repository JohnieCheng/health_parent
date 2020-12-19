package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Address;

import java.util.List;

public interface AddressManageDao {
    Page<Address> selectByCondition(String queryString);

    void deleteById(Integer id);

    void add(Address address);

    List<Address> findAll();
}
