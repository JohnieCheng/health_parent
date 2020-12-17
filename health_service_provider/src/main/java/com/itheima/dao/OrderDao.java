package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    void add(Order order);

    List<Order> findByCondition(Order order);

    Map findById4Detail(Integer id);

    List<Map> findHotSetMeal();

    Integer findOrderCountByDate(String date);

    Integer findOrderCountAfterDate(String date);

    Integer findVisitCountAfterDate(String date);

    Integer findVisitCountByDate(String date);


}
