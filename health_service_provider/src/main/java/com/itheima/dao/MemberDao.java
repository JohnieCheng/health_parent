package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Member;

public interface MemberDao {

    Member findByTel(String telephone);

    void add(Member member);

    Integer findMemberCountBeforeDate(String date);

    Integer findMemberCountByDate(String date);

    Integer findMemberTotalCount();

    Integer findMemberCountAfterDate(String date);

    Page<Member> selectByCondition(String queryString);

    Member findById(Integer id);



    void deleteById(Integer id);

    long findCountByMemberId(Integer id);

    void edit(Member member);
}
