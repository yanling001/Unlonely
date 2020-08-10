package com.hongyaoz.unlonelyupmsclient.Controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import com.hongyaoz.unlonelyupmsclient.dao.UserMapper;
import com.hongyaoz.unlonelyupmsclient.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/sso")
public class SSOController {
    // 全局会话key
    private final static String  SERVER_SESSION_ID = "server-session-id";
    // 全局会话key列表
    private final static String UNLONELY_UPMS_SERVER_SESSION_IDS = "unlonely-upms-server-session-ids";
    // code key
    private final static String SERVER_CODE = "server-code";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/login")
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response) {
       //校验有没有全局session
        String server_session = request.getParameter("server_session");
        if (!StringUtils.isBlank(server_session)){
            String code = (String) redisTemplate.opsForValue().get(server_session);
           Map <String,String> map=new HashMap<String, String>();
           map.put("code",code);
           map.put("server_session",server_session);
           return ServiceResponse.createBysuccessMessage(map);
        }
        String accountnum = request.getParameter("accountnum");
        String password = request.getParameter("password");
        if (StringUtils.isBlank(accountnum)) {
            return ServiceResponse.createByErrorMessage("帐号不能为空");
        }

        if (StringUtils.isBlank(password)) {
            return ServiceResponse.createByErrorMessage("密码不能为空");
        }
         //校验密码
       User userinfo = userMapper.selectpasswordbyaccountnum(accountnum);
        System.out.println(password +"  "+userinfo.getPassword());
        if (!password.equals(userinfo.getPassword())){
            return ServiceResponse.createByErrorMessage("密码错误");
        }
        //创建全局会话
        String key=SERVER_SESSION_ID+"_"+UUID.randomUUID();//Serialize
        String code=SERVER_CODE+"_"+UUID.randomUUID();
        redisTemplate.opsForValue().set(key, code);
        // 回跳登录前地址
        String backurl = request.getParameter("backurl");
        Map<String,String> map=new HashMap<String, String>();
        map.put("code",code);
        map.put("server_session",key);

        return  ServiceResponse.createBysuccessMessage("登录成功",map);

    }

    //校验code
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    @ResponseBody
    public Object code(HttpServletRequest request, HttpServletResponse response){

        String server_session = request.getHeader("server_session");
        String code = request.getHeader("code");
        System.out.println( server_session +"  "+ code);
        String realcode = (String) redisTemplate.opsForValue().get(server_session);
        if (!code.equals(realcode)){
            return ServiceResponse.createByErrorMessage("code错误");
        }
        String backurl = request.getHeader("backurl");
        System.out.println(backurl);
        String client_session = request.getHeader("client_session");
        redisTemplate.opsForHash().put(code,backurl,client_session);//存储次全局会话对应的局部会话
        Map<String,Boolean> map=new HashMap<String, Boolean>();
        map.put("istrue",true);
        return ServiceResponse.createBysuccessMessage(map);

    }


}
