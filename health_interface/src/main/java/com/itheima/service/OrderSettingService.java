package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Member;
import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(List<OrderSetting> data);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void add(Map<String,Object> map, Integer[] setmealIds);

    void delete(Integer id);
}
