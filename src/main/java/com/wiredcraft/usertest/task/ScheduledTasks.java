package com.wiredcraft.usertest.task;

import com.wiredcraft.usertest.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName ScheduledTasks
 * @Author lei 
 * @Description ScheduledTasks
 * @Date 12:16 2022/4/9
 * @Version 1.0
**/
@Component
@Slf4j
public class ScheduledTasks {

    @Resource
    UserService userService;

    /**
     * @Author lei 
     * @Description 定时任务
     * @Date 11:16 2022/4/9
     * @Param []
     * @return void
    **/
    @Scheduled(cron = "0 0/2 * * * ?")
    @Description("定时任务设置加载用户地理位置信息")
    public void setNearFriendToRedisGeoTask(){
        try {
            log.info("setNearFriendToRedisGeoTask...start....");
            userService.setNearFriendToRedisGeo();
            log.info("setNearFriendToRedisGeoTask...end....");
        } catch (Exception e) {
            log.error("任务跟进定时任务产生异常"+e.getMessage(),e);
        }
    }

}
