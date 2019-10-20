package com.tushuoBolg.component;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用户登陆验证。
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    //登陆错误次数保存标识.
    private final String loginFailId = "loginFailId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userInfromation = request.getAttribute("USERINFORMATION");
        JSONObject obj = JSONObject.fromObject(userInfromation);
        Iterator<String> userObj = obj.keys();
        Map<String, Object> userMap = new HashMap<>(); //存放转换结果
        while (userObj.hasNext()) {
            String userKey = userObj.next();
            userMap.put(userKey, obj.get(userKey));
        }
        String userId ="";
        for(Map.Entry<String, Object> entry : userMap.entrySet()){
            if(entry.getKey().equals("userId")){
                userId = entry.getValue().toString();
            }
        }
        request.getSession().removeAttribute(loginFailId);   //清空登陆次数.
        request.getSession().setAttribute("userId", userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
