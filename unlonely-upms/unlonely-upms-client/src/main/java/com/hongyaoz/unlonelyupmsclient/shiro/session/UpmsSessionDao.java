package com.hongyaoz.unlonelyupmsclient.shiro.session;

import com.hongyaoz.unlonelyupmsclient.util.SerializableUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * 基于redis的sessionDao，缓存共享session
 * Created by shuzheng on 2017/2/23.
 */
@Component
public class UpmsSessionDao extends CachingSessionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsSessionDao.class);
    // 会话key
    private final static String ZHENG_UPMS_SHIRO_SESSION_ID = "zheng-upms-shiro-session-id";
    // 全局会话key
    private final static String ZHENG_UPMS_SERVER_SESSION_ID = "zheng-upms-server-session-id";
    // 全局会话列表key
    private final static String ZHENG_UPMS_SERVER_SESSION_IDS = "zheng-upms-server-session-ids";
    // code key
    private final static String ZHENG_UPMS_SERVER_CODE = "zheng-upms-server-code";
    // 局部会话key
    private final static String ZHENG_UPMS_CLIENT_SESSION_ID = "zheng-upms-client-session-id";
    // 单点同一个code所有局部会话key
    private final static String ZHENG_UPMS_CLIENT_SESSION_IDS = "zheng-upms-client-session-ids";
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
       // RedisUtil.set(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
        redisTemplate.opsForValue().set(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
        //LOGGER.debug("doCreate >>>>> sessionId={}", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String session = redisTemplate.opsForValue().get(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        LOGGER.debug("doReadSession >>>>> sessionId={}", sessionId);
        return SerializableUtil.deserialize(session);
    }

    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止 没必要再更新了
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return;
        }
        // 更新session的最后一次访问时间
        UpmsSession upmsSession = (UpmsSession) session;
        UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());
        if (null != cacheUpmsSession) {
            upmsSession.setStatus(cacheUpmsSession.getStatus());
            upmsSession.setAttribute("FORCE_LOGOUT", cacheUpmsSession.getAttribute("FORCE_LOGOUT"));
        }
        redisTemplate.opsForValue().set(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
        // 更新ZHENG_UPMS_SERVER_SESSION_ID、ZHENG_UPMS_SERVER_CODE过期时间 TODO
        LOGGER.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sessionId = session.getId().toString();
        String upmsType = (String) session.getAttribute("zheng.upms.type");
        if ("client".equals(upmsType)) {
            // 删除局部会话和同一code注册的局部会话
       //     String code = redisTemplate.opsForValue().get(ZHENG_UPMS_CLIENT_SESSION_ID + "_" + sessionId);
            redisTemplate.delete(ZHENG_UPMS_CLIENT_SESSION_ID + "_" + sessionId);

        }
        if ("server".equals(upmsType)) {
            // 当前全局会话code
            String code = redisTemplate.opsForValue().get(ZHENG_UPMS_SERVER_SESSION_ID + "_" + sessionId);
            // 清除全局会话
            redisTemplate.delete(ZHENG_UPMS_SERVER_SESSION_ID + "_" + sessionId);
            // 清除code校验值
            redisTemplate.delete(ZHENG_UPMS_SERVER_CODE + "_" + code);
            // 清除所有局部会话

            List<String> clientSessionIds = redisTemplate.opsForList().range(ZHENG_UPMS_CLIENT_SESSION_IDS + "_" + code,0,-1);
            for (String clientSessionId : clientSessionIds) {
                redisTemplate.delete(ZHENG_UPMS_CLIENT_SESSION_ID + "_" + clientSessionId);
            }

        }
        // 删除session
        redisTemplate.delete(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        LOGGER.debug("doUpdate >>>>> sessionId={}", sessionId);
    }



    /**
     * 强制退出
     * @param ids
     * @return
     */
    public int forceout(String ids) {
        String[] sessionIds = ids.split(",");
        for (String sessionId : sessionIds) {
            // 会话增加强制退出属性标识，当此会话访问系统时，判断有该标识，则退出登录
            String session = redisTemplate.opsForValue().get(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
            UpmsSession upmsSession = (UpmsSession) SerializableUtil.deserialize(session);
            upmsSession.setStatus(UpmsSession.OnlineStatus.force_logout);
            upmsSession.setAttribute("FORCE_LOGOUT", "FORCE_LOGOUT");
            redisTemplate.opsForValue().set(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(upmsSession), (int) upmsSession.getTimeout() / 1000);
        }
        return sessionIds.length;
    }

    /**
     * 更改在线状态
     *
     * @param sessionId
     * @param onlineStatus
     */
    public void updateStatus(Serializable sessionId, UpmsSession.OnlineStatus onlineStatus) {
        UpmsSession session = (UpmsSession) doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        redisTemplate.opsForValue().set(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
    }

}
