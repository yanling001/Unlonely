package com.hongyaoz.unlonelystudyweb.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hongyaoz.unlonelystudyapi.sercvice.RoomService;
import com.hongyaoz.unlonelystudyweb.common.Message;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * <p>
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */

@RestController

@ServerEndpoint(value = "/webSocketRoom/{param}")
@EnableScheduling
public class WebSocketServer {

    @Reference
    RoomService roomService;
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private volatile static int onlineCount;
    //实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key为用户标识
    private static Map<String, CopyOnWriteArraySet<WebSocketServer>> connections = new ConcurrentHashMap<String, CopyOnWriteArraySet<WebSocketServer>>();
    private static Map<String, Queue<Message>> msg = new ConcurrentHashMap<String, Queue<Message>>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String room;
    private String role;
    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("param") String param, Session session) {
        this.session = session;
        String[] split = param.split("&");
        this.role = split[0];             //用户标识
        this.room=split[1];
        if (roomService.checkuserAndroom(Integer.parseInt(room),Integer.parseInt(role))) {
            roomService.opencalssroom(Integer.parseInt(room),Integer.parseInt(role));
            CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
            copyOnWriteArraySet.add(this);
            connections.put(room, copyOnWriteArraySet);     //添加到map中
            addOnlineCount();               // 在线房间数增加
        }else {
            CopyOnWriteArraySet<WebSocketServer> webSocketServers = connections.get(room);
            if (webSocketServers==null) return;
            webSocketServers.add(this);
            connections.put(room,webSocketServers);
        }

        System.out.println("有新连接加入！新用户：" + role + ",当前在线人数为" + getOnlineCount());
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        CopyOnWriteArraySet<WebSocketServer> webSocketServers = connections.get(room);
        if (roomService.checkuserAndroom(Integer.parseInt(room),Integer.parseInt(role))) {
            for (WebSocketServer webSocketServer:webSocketServers){
                webSocketServer.onClose();
            }
            connections.remove(room);
            subOnlineCount();          // 在线数减
        }
        webSocketServers.remove(role);

        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        JSONObject json = JSONObject.parseObject(message);
        System.out.println((String) json.get("msg"));
        String string = null;  //需要发送的信息
        String room = null;      //发送对象的用户标识
        if (json.get("story")!=null) {
            string = (String) json.get("story");
        }
        if (json.get("to")!=null) {
            room = (String) json.get("to");
        }
        Message ms=new Message();
        ms.setStory(string);
        ms.setFrom(role);//发送方
        ms.setMillion((String) json.get("million"));
        ms.setTime((String) json.get("time"));
        ms.setCode((Integer) json.get("code"));
        ms.setFromurl(json.getString("imageurl"));
       if (connections.containsKey(room))
        send(ms, room);


    }
    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
    //发送给指定角色
    private static void send(Message message, String to) {
        System.out.println("send  " + message.getStory() + "  " + message.getFrom() + "  " + to);
        try {
            //to指定用户
            CopyOnWriteArraySet<WebSocketServer> con = connections.get(to);
            for (WebSocketServer webSocketServer:con)
            if (webSocketServer != null) {
                webSocketServer.session.getBasicRemote().sendText(JSON.toJSONString(message));
            }


        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }

    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    @Scheduled(fixedRate=50*1000)
    private void configureTasks() throws Exception{
        Message message=new Message();
        message.setCode(2);
        for (Map.Entry<String,CopyOnWriteArraySet<WebSocketServer>> entry:WebSocketServer.connections.entrySet()){
            WebSocketServer.send(message,entry.getKey());
        }
    }

}


