package com.omo.mvc.interceptor;

import com.omo.bean.Admin;
import com.omo.constant.CrowdConstant;
import com.omo.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author MoYu
 * @create 2021-05-07 15:00
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1、创建Session对象，原因是如果登录了，会将用户保存在Session域中
        HttpSession session = request.getSession();

        // 2、从Session域中取出登录用户
        Admin loginAdmin = (Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);

        // 3、判断Session域中是否有该用户
        if(loginAdmin == null){

            // 4、没有就抛出自定义的异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        }

        // 5、如果Admin对象存在，则返回true放行
        return true;
    }
}
