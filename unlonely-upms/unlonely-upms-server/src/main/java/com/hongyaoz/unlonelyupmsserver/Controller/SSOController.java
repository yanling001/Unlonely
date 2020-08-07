package com.hongyaoz.unlonelyupmsserver.Controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/sso")
public class SSOController {
    // 全局会话key
    private final static String UNLONELY_UPMS_SERVER_SESSION_ID = "unlonely-upms-server-session-id";
    // 全局会话key列表
    private final static String UNLONELY_UPMS_SERVER_SESSION_IDS = "unlonely-upms-server-session-ids";
    // code key
    private final static String UNLONELY_UPMS_SERVER_CODE = "unlonely-upms-server-code";
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
  //      String rememberMe = request.getParameter("rememberMe");
        if (StringUtils.isBlank(username)) {
           // return new UpmsResult(UpmsResultConstant.EMPTY_USERNAME, "帐号不能为空！");
        }
        if (StringUtils.isBlank(password)) {
           // return new UpmsResult(UpmsResultConstant.EMPTY_PASSWORD, "密码不能为空！");
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        //String hasCode = redisTemplate.(UNLONELY_UPMS_SERVER_SESSION_ID + "_" + sessionId);
        ValueOperations<String, String > operations = redisTemplate.opsForValue();

      String hasCode=operations.get(UNLONELY_UPMS_SERVER_SESSION_ID + "_" + sessionId);

        // code校验值
        if (StringUtils.isBlank(hasCode)) {
            // 使用shiro认证
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

            subject.login(usernamePasswordToken);

            // 更新session状态
            upmsSessionDao.updateStatus(sessionId, UpmsSession.OnlineStatus.on_line);
            // 全局会话sessionId列表，供会话管理
            RedisUtil.lpush(UNLONELY_UPMS_SERVER_SESSION_IDS, sessionId.toString());
            // 默认验证帐号密码正确，创建code
            String code = UUID.randomUUID().toString();
            // 全局会话的code
            RedisUtil.set(UNLONELY_UPMS_SERVER_SESSION_ID + "_" + sessionId, code, (int) subject.getSession().getTimeout() / 1000);
            // code校验值
            RedisUtil.set(UNLONELY_UPMS_SERVER_CODE + "_" + code, code, (int) subject.getSession().getTimeout() / 1000);
        }
        // 回跳登录前地址
        String backurl = request.getParameter("backurl");
        if (StringUtils.isBlank(backurl)) {
         //   UpmsSystem upmsSystem = upmsSystemService.selectUpmsSystemByName(PropertiesFileUtil.getInstance().get("app.name"));
           // backurl = null == upmsSystem ? "/" : upmsSystem.getBasepath();
            return  ServiceResponse.createByErrorCodeMessage(1, backurl);
        } else {
            return new UpmsResult(UpmsResultConstant.SUCCESS, backurl);
        }
    }
}
