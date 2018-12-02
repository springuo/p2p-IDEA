package com.bjpowernode.p2p.interceptor;

import com.bjpowernode.p2p.common.constant.Constants;
import com.bjpowernode.p2p.model.user.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:UserInterceptor
 * Package:com.bjpowernode.p2p.interceptor
 * Description:
 *
 * @date:2018/9/27 14:33
 * @author:guoxin@bjpowernode.com
 */
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session中获取用户的信息
        User sessionUser = (User) request.getSession().getAttribute(Constants.SESSION_USER);

        //判断用户是否登录
        if (null == sessionUser) {
            //跳转至登录页面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
