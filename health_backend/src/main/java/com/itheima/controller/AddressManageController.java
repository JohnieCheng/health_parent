package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Address;
import com.itheima.service.AddressManageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addressManage")
public class AddressManageController {

    @Reference
    private AddressManageService addressManageService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = addressManageService.pageQuery(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            addressManageService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ADDRESS_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestParam String checkAddress, @RequestParam String longitude, @RequestParam String dimension) {
        try {
            Address address = new Address();
            address.setCheckAddress(checkAddress);
            address.setLongitude((double) Math.round(Double.parseDouble(longitude) * 1000) / 1000);
            address.setDimension((double) Math.round(Double.parseDouble(dimension) * 1000) / 1000);
//            System.out.println(checkAddress);
            addressManageService.add(address);
            return new Result(true, MessageConstant.ADD_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_ADDRESS_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public List<Address> findAll() {
        return addressManageService.findAll();
    }
}
