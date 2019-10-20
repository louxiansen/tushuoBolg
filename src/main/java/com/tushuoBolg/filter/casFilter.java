package com.tushuoBolg.filter;


import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hyrj on 2019/10/18.
 */
public class casFilter implements Filter {

    //cas服务地址
    private static String casServer;
    //应用服务器地址
    private static String applicationServer;

    @Value("${applicationServer}")
    public void setApplicationServer(String db) {
        applicationServer = db;
    }

    @Value("${casServer}")
    public void setCasServer(String db) {
        casServer = db;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String Url = request.getServletPath();
        //用户直接访问cas的退出地址
        if(Url.equals("/logout")){
            request.setAttribute("logout", true);
            chain.doFilter(request, response);
            return;
        }
        //从session 获取ticket
        String ticket = (String) request.getSession().getAttribute("ticket");
        //编码applicationVistPath
        String applicationVistPath = URLEncoder.encode(applicationServer+request.getServletPath(), "utf-8");
        //如果有ticket,验证ticket合法性，并获得用户信息
        if (StringUtils.isNotBlank(ticket)) {
            //根据ticket获取userId
            try {
                Map USERINFORMATION = doHttpClientForUser(ticket);
                if (USERINFORMATION != null && USERINFORMATION.size() > 0) {
                    //将userid存入request,放过请求
                    request.setAttribute("USERINFORMATION", USERINFORMATION);
                    chain.doFilter(request, response);
                    return;
                }else {
                    //进行ticket验证，换取用户信息，如果ticket无效，则删除session 中的ticket,并重定向到cas登陆页面。
                    request.getSession().removeAttribute("ticket");
                    response.sendRedirect(casServer+"/index?applicationVistPath="+applicationVistPath);
                    return;
                }
            } catch (Exception e) {
                //nothing todo
            }
        } else {
            //如果没有ticket,从request中获取code
            String code = (String) request.getParameter("code");
            //如果有code,用code 换ticket
            if (StringUtils.isNotBlank(code)){
                try{
                    //获得cas服务端存储的ticket
                    String ticketOnline = doHttpClientForTicket(code);
                    //将获取到的ticket放入到session
                    request.getSession().setAttribute("ticket", ticketOnline);
                    if(ticketOnline != null){
                        //根据ticket获取userId
                        Map USERINFORMATION = doHttpClientForUser(ticketOnline);
                        if (USERINFORMATION != null && USERINFORMATION.size() > 0) {
                            //将userid存入request,放过请求
                            request.setAttribute("USERINFORMATION", USERINFORMATION);
                            chain.doFilter(request, response);
                            return;
                        }
                    }else{
                        response.sendRedirect(casServer+"/index?applicationVistPath="+applicationVistPath);
                        return;
                    }
                }catch (Exception e){
                    //nothing todo
                }
            }else {
                //如果没有code,重定向到cas登陆页面,携带本应用访问地址的encode编码。
                response.sendRedirect(casServer+"/index?applicationVistPath="+applicationVistPath);
                return;
            }
        }
    }

    private static String doHttpClientForTicket(String code) throws Exception {
        //不允许重定向
        RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
        //创建http对象
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        //response 对象
        CloseableHttpResponse httpResponse = null;
        //get请求返回结果
        JSONObject jsonResult = null;
        //get请求的结果
        String resultScouce ="";
        //创建带参数的urls
        URI uri = new URIBuilder(casServer + "/code-to-ticket").setParameter("code", code).build();
        try {
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行http get请求
            httpResponse = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String strResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                jsonResult = JSONObject.fromObject(strResult);
                Iterator<String> nameItr = jsonResult.keys();//获取jsonObject的keySet()的迭代器
                String key;//接收key值
                Map<String, Object> outMap = new HashMap<>(); //存放转换结果
                while (nameItr.hasNext()) {
                    key = nameItr.next();
                    outMap.put(key, jsonResult.get(key));
                }
                for (Map.Entry<String, Object> entry : outMap.entrySet()) {
                    if (entry.getKey().equals("ticket") && !entry.getValue().equals(false)) {
                        resultScouce = entry.getValue().toString();
                        break;
                    }else {
                        return null;
                    }
                }
            }
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            httpClient.close();
        }
        return resultScouce;
    }


    private static Map doHttpClientForUser(String ticket) throws Exception {
        //不允许重定向
        RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
        //创建http对象
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        //response 对象
        CloseableHttpResponse httpResponse = null;
        //get请求返回结果
        JSONObject jsonResult = null;
        //get请求的结果
        Map resultScouce = new HashMap();
        //创建带参数的urls
        URI uri = new URIBuilder(casServer + "/ticket-to-user").setParameter("ticket", ticket).build();
        try {
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行http get请求
            httpResponse = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String strResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                jsonResult = JSONObject.fromObject(strResult);
                Iterator<String> nameItr = jsonResult.keys();//获取jsonObject的keySet()的迭代器
                String key;//接收key值
                Map<String, Object> outMap = new HashMap<>(); //存放转换结果
                while (nameItr.hasNext()) {
                    key = nameItr.next();
                    outMap.put(key, jsonResult.get(key));
                }
                for (Map.Entry<String, Object> entry : outMap.entrySet()) {
                    if(entry.getKey().equals("USERINFORMATION") && !entry.getValue().equals(false)) {
                        Object value = entry.getValue();
                        JSONObject obj = JSONObject.fromObject(value);
                        Iterator<String> userObj = obj.keys();
                        while (userObj.hasNext()) {
                            String userKey = userObj.next();
                            resultScouce.put(userKey, obj.get(userKey));
                        }
                        break;
                    }else {
                        return null;
                    }
                }
            }
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            httpClient.close();
        }
        return resultScouce;
    }
}
