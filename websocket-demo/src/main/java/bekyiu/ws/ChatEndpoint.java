package bekyiu.ws;

import bekyiu.utils.MessageUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wangyc
 * @Date: 2020/10/10 19:08
 * <p>
 * 每个连接都会创建一个Endpoint对象
 */
@ServerEndpoint("/chat/{userId}")
@Component
public class ChatEndpoint
{
    private static Map<String, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();
    private Session session;

    // 建立连接被调用
    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("userId") String userId)
    {
        System.out.println("ChatEndpoint.onOpen");
        this.session = session;
        onlineUsers.put(userId, this);
        String data = "[ 系统 ]: 用户 [ " + userId + " ] 已上线";
        bordercast(MessageUtils.buildJson(userId, null, true, data));
    }

    private void bordercast(String content)
    {

        onlineUsers.values().forEach(conn ->
        {
            try
            {
                conn.session.getBasicRemote().sendText(content);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    // 接收到客户端发送的数据时被调用
    @OnMessage
    public void onMessage(String msg, Session session)
    {

    }

    // 关闭连接时被调用
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId)
    {
        System.out.println(userId + ": 断开连接");
        onlineUsers.remove(userId);
        String data = "[ 系统 ]: 用户 [ " + userId + " ] 已下线";
        bordercast(MessageUtils.buildJson(userId, null, true, data));
    }
}
