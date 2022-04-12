package com.wiredcraft.usertest.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.usertest.common.enums.Constants;
import com.wiredcraft.usertest.common.enums.ResultCode;
import com.wiredcraft.usertest.common.exception.ServiceException;
import com.wiredcraft.usertest.common.utils.MD5Util;
import com.wiredcraft.usertest.common.utils.WebUtil;
import com.wiredcraft.usertest.common.vo.PageVO;
import com.wiredcraft.usertest.user.domain.po.User;
import com.wiredcraft.usertest.user.domain.ro.UserCreateRO;
import com.wiredcraft.usertest.user.domain.ro.UserListRO;
import com.wiredcraft.usertest.user.domain.ro.UserLoginRO;
import com.wiredcraft.usertest.user.domain.ro.UserModifyRO;
import com.wiredcraft.usertest.user.domain.vo.UserInfoVO;
import com.wiredcraft.usertest.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName UserController
 * @Author lei 
 * @Description 用户控制类
 * @Date 17:35 2022/4/8
 * @Version 1.0
**/
@RestController
@RequestMapping("user")
@Api("用户控制类")
@Slf4j
public class UserController {
    @Resource
    UserService userService;
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    /**
     * @Author lei 
     * @Description 查询用户信息
     * @Date 17:35 2022/4/8
     * @Param [id]
     * @return com.wiredcraft.usertest.user.domain.vo.UserInfoVO
    **/
    @GetMapping("{id}")
    @ApiOperation("查询用户信息")
    public UserInfoVO get(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return null;
        }
        UserInfoVO result = new UserInfoVO();
        //remove redundant fields
        BeanUtils.copyProperties(user, result);
        return result;
    }

    /**
     * @Author lei
     * @Description 分页查询用户列表
     * @Date 9:43 2022/4/9
     * @Param [userListRO]
     * @return com.wiredcraft.usertest.common.vo.PageVO<com.wiredcraft.usertest.user.domain.vo.UserInfoVO>
    **/
    @ApiOperation("分页查询用户列表")
    @GetMapping("list")
    public PageVO<UserInfoVO> list(@RequestBody UserListRO userListRO) {
        Page<User> page = WebUtil.getPage(userListRO);
        userService.lambdaQuery()
                .like(StringUtils.isNotBlank(userListRO.getName()), User::getName, userListRO.getName())
                .like(StringUtils.isNotBlank(userListRO.getAddress()), User::getAddress, userListRO.getAddress())
                .page(page);
        return WebUtil.transPage(page, UserInfoVO.class);

    }

    /**
     * @Author lei
     * @Description 新增用户
     * @Date 9:44 2022/4/9
     * @Param [userCreateRO]
     * @return java.lang.Long
    **/
//    @Anonymous
    @ApiOperation("新增用户")
    @PostMapping
    public Long create(@RequestBody @Valid UserCreateRO userCreateRO) {
        User user = new User();
        BeanUtils.copyProperties(userCreateRO, user);
        user.setPwd(MD5Util.encode(user.getPwd()));
        Optional<User> User = userService.lambdaQuery().eq(com.wiredcraft.usertest.user.domain.po.User::getName, userCreateRO.getName()).oneOpt();
        if (User.isPresent()) {
            throw new ServiceException(ResultCode.DUPLICATE_NAME);
        }
        user.setCreatedAt(new Date());
        user.setUpdateAt(user.getCreatedAt());
        userService.save(user);
        return user.getId();
    }

    /**
     * @Author lei 
     * @Description 删除用户
     * @Date 9:45 2022/4/9
     * @Param [id]
     * @return void
    **/
    @ApiOperation("删除用户")
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user!=null){
            userService.removeById(id);
        }else {
            throw new ServiceException(ResultCode.USER_NOT_EXISTS);
        }
    }

    /**
     * @Author lei
     * @Description 修改用户
     * @Date 9:45 2022/4/9
     * @Param [userModifyRO]
     * @return void
    **/
    @ApiOperation("修改用户")
    @PutMapping
    public void put(@RequestBody @Valid UserModifyRO userModifyRO) {
        User user = new User();
        BeanUtils.copyProperties(userModifyRO, user);
        user.setPwd(MD5Util.encode(user.getPwd()));
        userService.updateById(user);
    }


    /**
     * @Author lei
     * @Description 用户登录
     * @Date 9:46 2022/4/9
     * @Param [userLoginRO]
     * @return java.lang.String
    **/
//    @Anonymous
    @ApiOperation("用户登录")
    @PostMapping("login")
    public String login(@RequestBody @Valid UserLoginRO userLoginRO) {
        userLoginRO.setPwd(MD5Util.encode(userLoginRO.getPwd()));
        Optional<User> user = userService.lambdaQuery().eq(com.wiredcraft.usertest.user.domain.po.User::getName, userLoginRO.getName())
                .eq(com.wiredcraft.usertest.user.domain.po.User::getPwd, userLoginRO.getPwd()).oneOpt();
        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(Constants.USER_TOKEN_PREFIX + token, user.get(), 30, TimeUnit.MINUTES);
            return token;
        } else {
            throw new ServiceException(ResultCode.LOGIN_ERROR);
        }
    }

    /**
     * @Author lei
     * @Description //TODO
     * @Date 9:47 2022/4/9
     * @Param [id]
     * @return void
    **/
//    @PostMapping("friend/{id}")
//    public void friend(@PathVariable Long id) {
//        User loginUser = WebUtil.getLoginUser();
//        assert loginUser != null;
//        if (loginUser.getId().equals(id)) {
//            throw new ServiceException(ResultCode.ADD_SELF_ERROR);
//        }
//        //add current user's friend
//        User friend = userService.getById(id);
//        if (null == friend) {
//            throw new ServiceException(ResultCode.USER_NOT_EXISTS);
//        }
//        redisTemplate.opsForGeo().add(Constants.USER_FRIEND_PREFIX + loginUser.getId(),
//                new Point(friend.getLongitude(), friend.getLatitude()), id);
//        //todo user confirmation & other biz check
//        //add other user's friend
//        redisTemplate.opsForGeo().add(Constants.USER_FRIEND_PREFIX + id,
//                new Point(loginUser.getLongitude(), loginUser.getLatitude()), loginUser.getId());
//    }

    /**
     * @Author lei 
     * @Description 查询所有邻居
     * @Date 9:48 2022/4/9
     * @Param []
     * @return java.util.Set<java.lang.Object>
    **/
//    @ApiOperation("查询所有邻居")
//    @GetMapping("friend")
//    public Set<Object> getAllfriend() {
//        //todo page and get other info
//        return redisTemplate.opsForZSet().range(Constants.USER_FRIEND_PREFIX +
//                Objects.requireNonNull(WebUtil.getLoginUser()).getId(), 0, -1);
//    }


    /**
     * @Author lei
     * @Description 根据距离查询周围邻居
     * @Date 9:48 2022/4/9
     * @Param [distance]
     * @return org.springframework.data.geo.GeoResults<org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation<java.lang.Object>>
    **/
    @ApiOperation("根据距离查询周围邻居")
    @GetMapping("friend/near/{distance}")
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> nearfriend(String userName,@PathVariable Double distance) {
        Optional<User> user = userService.lambdaQuery().eq(com.wiredcraft.usertest.user.domain.po.User::getName, userName).oneOpt();
        if (!user.isPresent()) {
            throw new ServiceException(ResultCode.USER_NOT_EXISTS);
        }
        return userService.getNeighbour(user.get(),distance);
    }
}
