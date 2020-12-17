package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.SetMeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SetMealDao {

    void add(SetMeal setMeal);

    void setSetMealAndCheckGroup(HashMap<String, Integer> map);

    Page<SetMeal> selectByCondition(String queryString);

    SetMeal findById(Integer id);

    List<Integer> findGroupIdsBySetMealId(Integer id);

    void deleteAssociationBetweenSetMealIdsAndCheckGroupIds(Integer id);

    void edit(SetMeal setMeal);

    long findCountBySetMealId(Integer id);

    void deleteDataById(Integer id);

    List<SetMeal> findAll();

    SetMeal getById(int id);

    List<Map<String, Object>> findSetMealCount();
}
