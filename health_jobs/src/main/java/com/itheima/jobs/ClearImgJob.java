package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 自定义Job，实现定时清理垃圾图片
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        System.out.println("我执行了");
        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> set =
                jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                        RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set != null) {
            for (String picName : set) {
                //删除七牛云服务器上的图片
                boolean flag = QiniuUtils.deleteFileFromQiniu(picName);
                if(flag){
                    System.out.println("七牛云删除图片："+picName+"成功！");
                }else {
                    System.out.println("七牛云删除图片："+picName+"失败！");
                }
                //从Redis集合中删除图片名称
                //srem 命令用于移除集合中的一个或多个成员元素，不存在的成员元素会被忽略。当 key 不是集合类型，返回一个错误。
                jedisPool.getResource().
                        srem(RedisConstant.SETMEAL_PIC_RESOURCES, picName);
                System.out.println("自定义定时任务执行，删除垃圾图片:" + picName);
            }
        }
    }
}