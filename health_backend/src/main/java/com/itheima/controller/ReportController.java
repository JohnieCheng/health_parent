package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetMealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetMealService setMealService;

    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -12);

            List<String> list = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH, 1);
                list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
            }

            Map<String, Object> map = new HashMap<>();
            map.put("months", list);

            List<Integer> memberCount = memberService.findMemberCountByMonth(list);
            map.put("memberCount", memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping("/getSetMealReport")
    public Result getSetMealReport() {
        try {
            List<Map<String, Object>> list = setMealService.findSetMealCount();

            Map<String, Object> map = new HashMap<>();
            map.put("setMealCount", list);

            List<String> setMealNames = new ArrayList<>();

            for (Map<String, Object> m : list) {
                String name = (String) m.get("name");
                setMealNames.add(name);
            }
            map.put("setMealNames", setMealNames);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> result = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {

        try {
            Map<String, Object> businessReportData = reportService.getBusinessReportData();

            String reportDate = (String) businessReportData.get("reportDate");
            Integer todayIncreaseMember = (Integer) businessReportData.get("todayIncreaseMember");
            Integer totalMember = (Integer) businessReportData.get("totalMember");
            Integer thisWeekIncreaseMember = (Integer) businessReportData.get("thisWeekIncreaseMember");
            Integer thisMonthIncreaseMember = (Integer) businessReportData.get("thisMonthIncreaseMember");
            Integer todayOrderNum = (Integer) businessReportData.get("todayOrderNum");
            Integer thisWeekOrderNum = (Integer) businessReportData.get("thisWeekOrderNum");
            Integer thisMonthOrderNum = (Integer) businessReportData.get("thisMonthOrderNum");
            Integer todayVisitNum = (Integer) businessReportData.get("todayVisitNum");
            Integer thisWeekVisitNum = (Integer) businessReportData.get("thisWeekVisitNum");
            Integer thisMonthVisitNum = (Integer) businessReportData.get("thisMonthVisitNum");
            List<Map> hotSetMeal = (List<Map>) businessReportData.get("hotSetMeal");

            String templateRealPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";

            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayIncreaseMember);//今日新增

            sheet.getRow(4).getCell(7).setCellValue(totalMember);//总会员数

            sheet.getRow(5).getCell(5).setCellValue(thisWeekIncreaseMember);

            sheet.getRow(5).getCell(7).setCellValue(thisMonthIncreaseMember);

            sheet.getRow(7).getCell(5).setCellValue(todayOrderNum);

            sheet.getRow(7).getCell(7).setCellValue(todayVisitNum);

            sheet.getRow(8).getCell(5).setCellValue(thisWeekOrderNum);

            sheet.getRow(8).getCell(7).setCellValue(thisWeekVisitNum);

            sheet.getRow(9).getCell(5).setCellValue(thisMonthOrderNum);

            sheet.getRow(9).getCell(7).setCellValue(thisMonthVisitNum);


            int rowNum = 12;
            for (Map map : hotSetMeal) {
                String setMealName = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(setMealName);
                row.getCell(5).setCellValue(setmeal_count);
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();
            return null;
//            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }
}
