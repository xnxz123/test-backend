package com.wiredcraft.usertest.common.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.usertest.common.enums.Constants;
import com.wiredcraft.usertest.common.vo.PageVO;
import com.wiredcraft.usertest.user.domain.po.User;
import com.wiredcraft.usertest.user.domain.ro.UserListRO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WebUtil {
    private static RedisTemplate<String, Object> redisTemplate;

//    private static NamedThreadLocal<User> bizUserNamedThreadLocal = new NamedThreadLocal<>("login_user");
//    private static NamedThreadLocal<Boolean> hasLoginUser = new NamedThreadLocal<>("hasLoginUser");

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ra == null) {
            return null;
        }
        HttpServletRequest request = ra.getRequest();
        return request;
    }



    public static User getLoginUser() {
//        if (hasLoginUser.get() != null && hasLoginUser.get()) {
//            return bizUserNamedThreadLocal.get();
//        }
//        if (hasLoginUser.get() != null && !hasLoginUser.get()) {
//            return null;
//        }
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
//            hasLoginUser.set(false);
            return null;
        }
        String key = Constants.USER_TOKEN_PREFIX + token;
        User bizUser = (User) redisTemplate.opsForValue().get(key);
        if (null != bizUser) {
            //extend expiration time
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
//            bizUserNamedThreadLocal.set(bizUser);
//            hasLoginUser.set(true);
            return bizUser;
        } else {
//            hasLoginUser.set(false);
            return null;
        }
    }

//    public static void clear() {
//        hasLoginUser.remove();
//        bizUserNamedThreadLocal.remove();
//    }


    @Autowired
    public void setRedisTemplate(RedisTemplate<String,Object> redisTemplate) {
        WebUtil.redisTemplate = redisTemplate;
    }

    public static <T> Page<T> getPage(UserListRO baseRequest) {
        LinkedList<OrderItem> orderItems = new LinkedList<>();
        if (StringUtils.isNotBlank(baseRequest.getOrderBy())) {
            orderItems.add(new OrderItem(baseRequest.getOrderBy(), baseRequest.getAsc()));
        }
        if (baseRequest.getPageSize() != null && baseRequest.getPageNum() != null) {
            Page<T> tPage = new Page<>(baseRequest.getPageNum(), baseRequest.getPageSize());
            tPage.setOrders(orderItems);
            return tPage;
        } else {
            //get all
            Page<T> tPage = new Page<>(0, -1);
            tPage.setOrders(orderItems);
            return tPage;
        }
    }

    public static <T> List<T> transListVO(List list, Class<T> tClass) {
        return JSON.parseArray(JSON.toJSONString(list), tClass);
    }

    public static <T> PageVO<T> transPage(Page page, Class<T> tClass) {
        PageVO<T> result = new PageVO<>();
        result.setTotal(page.getTotal());
        result.setDataList(transListVO(page.getRecords(), tClass));
        return result;
    }


    public static <T> T transVO(Object object, Class<T> tClass) {
        return JSON.parseObject(JSON.toJSONString(object), tClass);
    }


}
