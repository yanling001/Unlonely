package com.hongyaoz.unlonelyupmsserver.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSOController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSOController.class);
    // 全局会话key
    private final static String ZHENG_UPMS_SERVER_SESSION_ID = "zheng-upms-server-session-id";
    // 全局会话key列表
    private final static String ZHENG_UPMS_SERVER_SESSION_IDS = "zheng-upms-server-session-ids";
    // code key
    private final static String ZHENG_UPMS_SERVER_CODE = "zheng-upms-server-code";
}
