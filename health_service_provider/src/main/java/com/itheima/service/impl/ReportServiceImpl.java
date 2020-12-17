package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() {
        String today = null;
        String thisWeekMonday = null;
        String first4ThisMonth = null;
        try {
            today = DateUtils.parseDate2String(DateUtils.getToday());
            thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            first4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer todayIncreaseMember = memberDao.findMemberCountByDate(today);
        Integer totalMember = memberDao.findMemberTotalCount();
        Integer thisWeekIncreaseMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        Integer thisMonthIncreaseMember = memberDao.findMemberCountAfterDate(first4ThisMonth);

        Integer todayOrderNum = orderDao.findOrderCountByDate(today);
        Integer thisWeekOrderNum = orderDao.findOrderCountAfterDate(thisWeekMonday);
        Integer thisMonthOrderNum = orderDao.findOrderCountAfterDate(first4ThisMonth);

        Integer todayVisitNum = orderDao.findVisitCountByDate(today);
        Integer thisWeekVisitNum = orderDao.findVisitCountAfterDate(thisWeekMonday);
        Integer thisMonthVisitNum = orderDao.findVisitCountAfterDate(first4ThisMonth);

        List<Map> hotSetMeal = orderDao.findHotSetMeal();

        Map<String, Object> result = new HashMap<>();
        result.put("reportDate", today);
        result.put("todayIncreaseMember", todayIncreaseMember);
        result.put("totalMember", totalMember);
        result.put("thisWeekIncreaseMember", thisWeekIncreaseMember);
        result.put("thisMonthIncreaseMember", thisMonthIncreaseMember);
        result.put("todayOrderNum", todayOrderNum);
        result.put("thisWeekOrderNum", thisWeekOrderNum);
        result.put("thisMonthOrderNum", thisMonthOrderNum);
        result.put("todayVisitNum", todayVisitNum);
        result.put("thisWeekVisitNum", thisWeekVisitNum);
        result.put("thisMonthVisitNum", thisMonthVisitNum);
        result.put("hotSetMeal", hotSetMeal);

        return result;
    }
}
