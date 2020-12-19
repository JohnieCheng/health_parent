package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.AddressManageDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Address;
import com.itheima.service.AddressManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = AddressManageService.class)
@Transactional
public class AddressManageServiceImpl implements AddressManageService {

    @Autowired
    private AddressManageDao addressManageDao;

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage, pageSize);
        Page<Address> page = addressManageDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<Address> rows = page.getResult();
        return new PageResult(total, rows);
    }

    @Override
    public void deleteById(Integer id) {
        addressManageDao.deleteById(id);
    }

    @Override
    public void add(Address address) {
        addressManageDao.add(address);
    }

    @Override
    public List<Address> findAll() {
        return addressManageDao.findAll();
    }
}
