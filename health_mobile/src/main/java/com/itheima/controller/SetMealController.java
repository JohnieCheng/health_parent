package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.SetMeal;
import com.itheima.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @RequestMapping("/getAllSetMeal")
    public Result getAllSetMeal() {
        try {
            List<SetMeal> setMealList = setMealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setMealList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里");
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }
//    根据套餐D査询套餐详情（套餐基本信息、套餐对应的检查组信息、检査组对应的检查项信息
    @RequestMapping("/getById")
    public Result getById(int id) {
        try {
            SetMeal setMeal = setMealService.getById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setMeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
