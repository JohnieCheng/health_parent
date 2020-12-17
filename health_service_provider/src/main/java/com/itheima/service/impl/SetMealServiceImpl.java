package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetMealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.SetMeal;
import com.itheima.service.SetMealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${mobile_out_put_path}")
    private String mobileOutPutPath;
//    @Value("${mobile_out_put_path_server}")
//    private String mobileOutPutPath_server;

    @Override
    public void add(SetMeal setMeal, Integer[] checkgroupIds) {
        setMealDao.add(setMeal);
        setSetMealAndCheckGroup(setMeal, checkgroupIds);
        //将图片名称保存到Redis
        if (null != setMeal.getImg()) {
            savePic2Redis(setMeal.getImg());
        }
        generateMobileStaticHtml();
    }

    //    将图片名称保存到Redis
    private void savePic2Redis(String img) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
    }

    public void generateMobileStaticHtml() {
        List<SetMeal> setMealList = setMealDao.findAll();
        generateMobileSetMealListHtml(setMealList);
        generateMobileSetMealDetailHtml(setMealList);
    }

    public void generateMobileSetMealListHtml(List<SetMeal> setMealList) {
        Map map = new HashMap();
        map.put("setMealList", setMealList);
        generateHtml("mobile_setmeal.ftl", "m_setmeal.html", map);
    }

    public void generateMobileSetMealDetailHtml(List<SetMeal> setMealList) {
        for (SetMeal setMeal : setMealList) {
            Map map = new HashMap();
            map.put("setMeal", setMealDao.getById(setMeal.getId()));
            generateHtml("mobile_setmeal_detail.ftl", "setmeal_detail_" + setMeal.getId() + ".html", map);
        }
    }

    //生成静态页面
    public void generateHtml(String templateName, String htmlPageName, Map map) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);
            out = new FileWriter(new File(mobileOutPutPath + "/" + htmlPageName));
            template.process(map, out);
            out.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<SetMeal> page = setMealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public SetMeal findById(Integer id) {
        return setMealDao.findById(id);
    }

    @Override
    public List<Integer> findGroupIdsBySetMealId(Integer id) {
        return setMealDao.findGroupIdsBySetMealId(id);
    }

    @Override
    public void edit(SetMeal setMeal, Integer[] checkgroupIds) {
//        先清除关联关系，再关联
        setMealDao.deleteAssociationBetweenSetMealIdsAndCheckGroupIds(setMeal.getId());
        setSetMealAndCheckGroup(setMeal, checkgroupIds);
        setMealDao.edit(setMeal);
//将图片名称保存到Redis
        if (null != setMeal.getImg()) {
            savePic2Redis(setMeal.getImg());
        }
        //生成静态页面
        generateMobileStaticHtml();
    }

    @Override
    public void deleteDataById(Integer id) {
        long count = setMealDao.findCountBySetMealId(id);
        if (count > 0) {
            new RuntimeException();
        }
        setMealDao.deleteAssociationBetweenSetMealIdsAndCheckGroupIds(id);
        setMealDao.deleteDataById(id);
        //删除静态页面
        File file = new File(mobileOutPutPath + "/setmeal_detail_" + id + ".html");
        //        System.out.println(file.exists());
        if (file.exists()) {
            //把图片地址存进redis,使用定时任务删除服务器里的静态页面
//            String path = mobileOutPutPath_server+"/setmeal_detail_" + id + ".html";
//            jedisPool.getResource().sadd(RedisConstant.SETMEAL_STATIC_PAGE__RESOURCES, path);
            file.delete();
            //重新生成静态信息列表页面
            List<SetMeal> setMealList = setMealDao.findAll();
            generateMobileSetMealListHtml(setMealList);
            System.out.println("删除" + file.getName() + "成功！");
        } else {
            System.out.println("文件不存在，删除" + file.getName() + "失败！");
        }
        //        System.out.println(file.exists());

    }

    @Override
    public List<SetMeal> findAll() {
        return setMealDao.findAll();
    }

    @Override
    public SetMeal getById(int id) {
        return setMealDao.getById(id);
    }

    @Override
    public List<Map<String, Object>> findSetMealCount() {
        return setMealDao.findSetMealCount();
    }


    private void setSetMealAndCheckGroup(SetMeal setmeal, Integer[] checkgroupIds) {
        Integer setmealId = setmeal.getId();
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("setmeal_id", setmealId);
                map.put("checkgroup_id", checkgroupId);
                setMealDao.setSetMealAndCheckGroup(map);
            }
        }
    }
}
