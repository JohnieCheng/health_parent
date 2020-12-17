package com.itheima.dao;

import com.itheima.pojo.Member;

public interface MemberDao {

    Member findByTel(String telephone);

    void add(Member member);

    Integer findMemberCountBeforeDate(String date);

    Integer findMemberCountByDate(String date);

    Integer findMemberTotalCount();

    Integer findMemberCountAfterDate(String date);

}
