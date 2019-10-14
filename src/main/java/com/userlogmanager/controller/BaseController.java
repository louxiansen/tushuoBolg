package com.userlogmanager.controller;

import com.userlogmanager.util.SessionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器基础类，主要用于封装一些controller里面的公用方法。
 */
@Controller
public abstract class BaseController {

    @Resource
    protected SessionUtil sessionUtil;
    @Value("${website.loginId}")
    protected String sessionLoginId;

    /**
     * 返回json数据视图。
     * @param jsonData
     * @return
     */
    protected ModelAndView jsonView(Map<String, Object> jsonData){
        MappingJackson2JsonView json = new MappingJackson2JsonView();
        json.setAttributesMap(jsonData);
        return new ModelAndView(json);
    }

    /**
     * 返回Json格式成功结果。
     * @return
     */
    protected ModelAndView successJsonView(){
        MappingJackson2JsonView json = new MappingJackson2JsonView();
        json.setAttributesMap(new HashMap<String, Object>(){{
            put("success", true);
        }});
        return new ModelAndView(json);
    }

    protected ModelAndView successJsonView(final String dataKey, final Object data){
        MappingJackson2JsonView json = new MappingJackson2JsonView();
        json.setAttributesMap(new HashMap<String, Object>(){{
            put("success", true);
            put(dataKey, data);
        }});
        return new ModelAndView(json);
    }

    /**
     * 返回json格式数据.
     * @param data
     * @return
     */
    protected ModelAndView successJsonView(Map<String, Object> data){
        data.put("success", true);
        MappingJackson2JsonView json = new MappingJackson2JsonView();
        json.setAttributesMap(data);
        return new ModelAndView(json);
    }

    protected ModelAndView failureJsonView(){
        MappingJackson2JsonView json = new MappingJackson2JsonView();
        json.setAttributesMap(new HashMap<String, Object>(){{
            put("success", false);
        }});
        return new ModelAndView(json);
    }

    protected ModelAndView failureJsonView(final String errMsg){
        MappingJackson2JsonView json = new MappingJackson2JsonView();
        json.setAttributesMap(new HashMap<String, Object>(){{
            put("success", false);
            put("errMsg", errMsg);
        }});
        return new ModelAndView(json);
    }

    /**
     * 跳转到错误页面，显示错误消息.
     * @param errorMsg
     * @return
     */
    protected ModelAndView errorPage(final String errorMsg){
        return new ModelAndView("error_page", new HashMap<String, Object>(){{
            put("error_msg", errorMsg);
        }});
    }

    /**
     * 提示成功信息页面.
     * @param successMsg
     * @return
     */
    protected ModelAndView successPage(final String successMsg){
        return new ModelAndView("success_page", new HashMap<String, Object>(){{
            put("success_msg", successMsg);
        }});
    }
}
