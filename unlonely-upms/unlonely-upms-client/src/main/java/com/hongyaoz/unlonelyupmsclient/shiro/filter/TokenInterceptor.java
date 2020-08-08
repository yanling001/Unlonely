package com.hongyaoz.unlonelyupmsclient.shiro.filter;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hongyaoz.unlonelycommon.Until.HttpUtil;
import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    private final static String CLIENT_SESSION_ID="client_sessionid";
    @Value("${ssourl.server_url}")
    String server_url;
    @Value("${ssourl.client_url}")
    String client_url;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,String> map=new HashMap<String, String>();
        map.put("server_url",server_url);
        String sessionkey = request.getHeader("client_sessionid");
        if (!StringUtils.isBlank(sessionkey)){

            if (redisTemplate.hasKey(sessionkey)){
                redisTemplate.expire(sessionkey,2,TimeUnit.HOURS);
                return true;
            }
        }
        //没有本地session 判断又没有code
        String code = request.getHeader("code");
        if (!StringUtils.isBlank(code)){
            String key = CLIENT_SESSION_ID+"_"+ UUID.randomUUID();
            String url=server_url+"?"+"backurl="+client_url+"&"+"code="+code+"&"+"client_session="+key;
            JSONObject get = HttpUtil.GET(url);
            if (get.getBoolean("istrue")){//code校验成功
                //设置本地会话
              redisTemplate.expire(key,2, TimeUnit.HOURS);//2小时过期
                response.setHeader("client_sessionid",key);
            }else {
                PrintWriter writer = response.getWriter();
                response.setHeader("Content-type","application/json");
                writer.write(JSON.toJSONString(ServiceResponse.createByErrorMessage("code错误请重新登录",map)));
                writer.flush();
                writer.close();
                return false;
            }
        }
        // 没有code 重定向
        PrintWriter writer = response.getWriter();
        response.setHeader("Content-type","application/json");

        writer.write(JSON.toJSONString(ServiceResponse.createByErrorMessage("凭证过期请重新登录",map)));
        writer.flush();
        writer.close();


        return false;
    }
}