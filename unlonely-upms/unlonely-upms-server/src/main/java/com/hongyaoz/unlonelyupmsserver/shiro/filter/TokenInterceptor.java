package com.hongyaoz.unlonelyupmsserver.shiro.filter;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hongyaoz.unlonelycommon.Until.HttpUtil;
import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final static String CLIENT_SESSION_ID="client_sessionid";
  @Value("${ssourl.server_url}")
    String server_url;
    @Value("${ssourl.client_url}")
    String client_url;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("req test");
        Map<String,String> map=new HashMap<String, String>();
        map.put("server_url",server_url+"/login");
        String sessionkey = request.getHeader("client_sessionid");
        if (!StringUtils.isBlank(sessionkey)){
            System.out.println(sessionkey);
            if (redisTemplate.hasKey(sessionkey)){
                System.out.println("------->");
                redisTemplate.expire(sessionkey,2,TimeUnit.HOURS);
                return true;
            }
        }
        //没有本地session 判断又没有code
        String code = request.getHeader("code");
        if (!StringUtils.isBlank(code)){
            String key = CLIENT_SESSION_ID+"_"+ UUID.randomUUID();
            String url=server_url+"/code";
            System.out.println(client_url);

            int socketTimeout = 120 * 1000;
            int connectTimeout = 120 * 1000;

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("code",code);
            headers.put("client_session",key);
            headers.put("server_session",request.getHeader("server_session"));
            headers.put("backurl",client_url);
            headers.put("Content-Type", "application/json");
            String msg = HttpUtil.sendPost(url, socketTimeout, connectTimeout, headers, map, null);

            JSONObject get =JSONObject.parseObject(msg);

            System.out.println(msg);
            JSONObject result = get.getJSONObject("result");
            if (result.getBoolean("istrue")){//code校验成功
                //设置本地会话
              redisTemplate.opsForValue().set(key,"local_session");//2小时过期
                response.setHeader("client_sessionid",key);
                return true;
            }else {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(ServiceResponse.createByErrorMessage("code错误请重新登录",map)));
                writer.flush();
                writer.close();
                return false;
            }
        }
        // 没有code 重定向
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        //content-type: text/html; charset=utf-8

       // response.setHeader("charset","utf-8");

        writer.write(JSON.toJSONString(ServiceResponse.createByErrorMessage("凭证过期请重新登录",map)));
        writer.flush();
        writer.close();


        return false;
    }
}