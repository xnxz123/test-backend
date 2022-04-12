package com.wiredcraft.usertest.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wiredcraft.usertest.user.domain.po.User;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;

/**
 * @ClassName UserService
 * @Author lei
 * @Description 用户service
 * @Date 10:34 2022/4/9
 * @Version 1.0
**/
public interface UserService extends IService<User> {

    GeoResults<RedisGeoCommands.GeoLocation<Object>> getNeighbour (User user,Double distance);

    void setNearFriendToRedisGeo();
}
