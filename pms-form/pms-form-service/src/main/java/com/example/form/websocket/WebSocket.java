package com.example.form.websocket;

import com.example.exception.PMSException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author GLaDOS
 * @date 2023/6/1 15:31
 */
@Data
@Slf4j
public class WebSocket {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //用户id
    private String userId;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //  注：底下WebSocket是当前类名
    private static final CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static final AtomicInteger ONLINE = new AtomicInteger(0);

    // 用来存在线连接用户信息
    private static final ConcurrentHashMap<String, Session> SESSION_POOL = new ConcurrentHashMap<String,Session>();

    public ConcurrentHashMap<String,Session> getSessionPool(){
        return SESSION_POOL;
    }

    public AtomicInteger getOnline(){
        return ONLINE;
    }

    public CopyOnWriteArraySet<WebSocket> getWebSockets(){
        return webSockets;
    }

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")String userId) {
        try {
            this.session = session;
            this.userId = userId;
            ONLINE.incrementAndGet();
            SESSION_POOL.put(userId, session);
            log.info("用户的id为：{}", session.getId());
            log.info("【websocket消息】有新的连接，总数为:{}", ONLINE.get());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PMSException(e.getMessage());
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            ONLINE.decrementAndGet();
            SESSION_POOL.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:{}", ONLINE.get());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PMSException(e.getMessage());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端消息
     * @param session 某个客户端的连接会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("【websocket消息】收到客户端消息:{}", message);
    }

    /** 发送错误时的处理
     * @param session 某个客户端的连接会话
     * @param error 错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("{}用户错误,原因:{}", session.getId(), error.getMessage());
        error.printStackTrace();
    }

    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:{}", message);
        for(WebSocket webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = SESSION_POOL.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:{}", message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            Session session = SESSION_POOL.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:{}", message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
