package com.wiredcraft.usertest.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wiredcraft.usertest.common.enums.Constants;
import com.wiredcraft.usertest.common.utils.WebUtil;
import com.wiredcraft.usertest.user.domain.po.User;
import com.wiredcraft.usertest.user.mapper.UserMapper;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName UserServiceImpl
 * @Author lei 
 * @Description 用户serviceImpl
 * @Date 10:34 2022/4/9
 * @Version 1.0
**/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> getNeighbour (User user,Double distance) {
        assert user != null;
        Circle circle = new Circle(new Point(user.getLongitude(), user.getLatitude()),
                new Distance(distance, Metrics.KILOMETERS));
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending();
        GeoResults<RedisGeoCommands.GeoLocation<Object>> nearFriends = redisTemplate.opsForGeo().radius(
                Constants.USER_FRIEND_PREFIX + user.getId(), circle, args);
        return nearFriends;
    }
    @Override
    public void setNearFriendToRedisGeo(){
        List<User> userList = this.list();
        if (userList!=null&&userList.size()>0){
            for (User user : userList) {
                redisTemplate.opsForGeo().add(Constants.USER_FRIEND_PREFIX + user.getId(),
                        new Point(user.getLongitude(), user.getLatitude()), user.getId());
            }
        }
    }

}




