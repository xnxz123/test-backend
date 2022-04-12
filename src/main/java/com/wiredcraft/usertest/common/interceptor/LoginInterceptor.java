package com.wiredcraft.usertest.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.wiredcraft.usertest.common.enums.ResultCode;
import com.wiredcraft.usertest.common.exception.ServiceException;
import com.wiredcraft.usertest.common.utils.WebUtil;
import com.wiredcraft.usertest.user.domain.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    //调用请求的时候执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        if(handler instanceof HandlerMethod) {
//            HandlerMethod h = (HandlerMethod)handler;
//            Anonymous methodAnnotation = h.getMethodAnnotation(Anonymous.class);
//            if (null != methodAnnotation) {
//                return true;
//            }
//        }
//        if (request.getContextPath().contains("doc.html")){
//            return true;
//        }
        User loginUser = WebUtil.getLoginUser();
        if (loginUser == null) {
            throw new ServiceException(ResultCode.REQUIRE_LOGIN);
        }
        return true;
    }


//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info(request.getContextPath());
//        log.info(request.getMethod());
//        log.info(JSON.toJSONString(request.getParameterMap()));
//        log.info(JSON.toJSONString(response.getWriter()));
//    }
}