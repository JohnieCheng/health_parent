package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;
import com.itheima.pojo.OrderSetting;

import java.util.*;

public interface OrderSettingDao {

    void add(OrderSetting orderSetting);

    void editNumberByOrderDate(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    List<OrderSetting> getOrderSettingByMonth(Map date);

    OrderSetting findByOrderDate(Date OrderDate);

    void editReservationsByOrderDate(OrderSetting orderSetting);

    Page<List<Map>> selectByCondition(String queryString);

    Integer findIdByNumberOrIdCard(Map<String, Object> map);

    void addOrder(Map<String, Object> map);


    void deleteById(Integer id);
}
