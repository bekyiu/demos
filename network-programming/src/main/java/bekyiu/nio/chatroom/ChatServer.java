package bekyiu.nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class ChatServer
{
    private static final Integer DEFAULT_PORT = 6140;
    private static final String $EXIT = "$exit";

    private int port;
    private ServerSocketChannel channel;
    private Selector selector;
    private ByteBuffer rBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer wBuffer = ByteBuffer.allocate(1024);
    private Charset charset = StandardCharsets.UTF_8;

    public ChatServer(int port)
    {
        this.port = port;
    }

    public ChatServer()
    {
        this(DEFAULT_PORT);
    }

    public boolean readyToExit(String msg)
    {
        return $EXIT.equals(msg);
    }

    public void start()
    {
        try
        {
            channel = ServerSocketChannel.open();
            // 开启非阻塞模式
            channel.configureBlocking(false);
            // 得到与channel相关的ServerSocket并绑定端口
            channel.socket().bind(new InetSocketAddress(port));
            selector = Selector.open();
            // 让selector监控channel上的accept事件
            channel.register(selector, SelectionKey.OP_ACCEPT);
            while (true)
            {
                // 返回 监控到的所有通道上触发的事件 的个数
                // 如果一个也没触发， 就阻塞
                selector.select();
                // 获取被触发事件的相关信息
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys)
                {
                    handle(key);
                }
                // 要清空， 不然下一次循环还有这一次的数据
                selectionKeys.clear();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            close();
        }
    }

    private void handle(SelectionKey key) throws IOException
    {
        // 如果是accept事件(和客户端建立了连接)被触发
        if (key.isAcceptable())
        {
            // 返回当前key所属的通道， 返回的是 this.channel
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            // 返回一个用户和客户端通信的SocketChannel
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            // 让selector监控该client通道上的read事件, 以便该客户端向server发送信息时，可以处理
            client.register(selector, SelectionKey.OP_READ);
            System.out.println("客户端: [" + getClientText(client.socket()) + "]进入聊天室...");
        }
        // 如果有客户端发来消息
        else if (key.isReadable())
        {
            SocketChannel client = (SocketChannel) key.channel();
            // 接收客户端发来的消息
            String fwdMsg = receive(client);
            // 客户端异常
            if (fwdMsg.isEmpty())
            {
                // 取消 key所属的通道的响应事件的监听
                key.cancel();
                // selector的状态有所变化, select函数重新执行
                selector.wakeup();
            }
            else
            {
                // 转发消息, 不要转发给自己
                forwardMessage(client, fwdMsg);
                if(readyToExit(fwdMsg))
                {
                    key.cancel();
                    selector.wakeup();
                    System.out.println(getClientText(client.socket()) + "已下线...");
                }
            }
        }
    }

    private void forwardMessage(SocketChannel client, String fwdMsg) throws IOException
    {
        // 获取所有在selector中注册了的key
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys)
        {
            Channel channel = key.channel();
            // 不转发给ServerSocketChannel
            if (channel instanceof ServerSocketChannel)
            {
                continue;
            }
            // 不转发给自己
            if (key.isValid() && !channel.equals(client))
            {
                wBuffer.clear();
                wBuffer.put(charset.encode(getClientText(client.socket()) + ": " + fwdMsg));
                wBuffer.flip();
                while (wBuffer.hasRemaining())
                {
                    ((SocketChannel) channel).write(wBuffer);
                }
            }
        }
    }

    private String receive(SocketChannel client) throws IOException
    {
        rBuffer.clear();
        while (client.read(rBuffer) > 0) ;
        rBuffer.flip();
        return String.valueOf(charset.decode(rBuffer));
    }

    public String getClientText(Socket socket)
    {
        // ip:port
        return socket.getInetAddress().getHostAddress()
                + ":" + socket.getPort();
    }

    public void close()
    {
        try
        {
            if (selector != null)
            {
                selector.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        ChatServer server = new ChatServer(7777);
        server.start();
    }
}
