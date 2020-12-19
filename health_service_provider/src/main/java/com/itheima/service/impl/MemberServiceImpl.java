package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MemberDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTel(String telephone) {
        return memberDao.findByTel(telephone);
    }

    @Override
    public void add(Member member) {
        if (member.getPassword() != null) {
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> month) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String m : month) {
            m = m + ".31";
            Integer count = memberDao.findMemberCountBeforeDate(m);
            list.add(count);
        }
        return list;

    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<Member> page = memberDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<Member> rows = page.getResult();
        return new PageResult(total,rows);
    }

    @Override
    public Member findById(Integer id) {
        return memberDao.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        memberDao.deleteById(id);
    }

    @Override
    public void edit(Member member) {
        memberDao.edit(member);
    }

}
