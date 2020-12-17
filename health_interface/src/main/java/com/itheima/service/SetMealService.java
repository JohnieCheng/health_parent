package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.SetMeal;

import java.util.List;
import java.util.Map;

public interface SetMealService {

    void add(SetMeal setMeal, Integer[] checkgroupIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    SetMeal findById(Integer id);

    List<Integer> findGroupIdsBySetMealId(Integer id);

    void edit(SetMeal setMeal, Integer[] setmealIds);

    void deleteDataById(Integer id);

    List<SetMeal> findAll();

    SetMeal getById(int id);

    List<Map<String, Object>> findSetMealCount();

//    void deleteStaticPage(SetMeal setMeal);

}
