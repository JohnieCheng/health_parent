package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Member;

import java.util.List;

public interface MemberService
{
    Member findByTel(String telephone);

    void add(Member member);

    List<Integer> findMemberCountByMonth(List<String> list);

    PageResult pageQuery(QueryPageBean queryPageBean);

    Member findById(Integer id);

    void deleteById(Integer id);

    void edit(Member member);
}
