package bekyiu.nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class ChatServer {
    private static final Integer DEFAULT_PORT = 6140;
    private static final String $EXIT = "$exit";

    private int port;
    private ServerSocketChannel channel;
    private Selector selector;
    private ByteBuffer rBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer wBuffer = ByteBuffer.allocate(1024);
    private Charset charset = StandardCharsets.UTF_8;

    public ChatServer(int port) {
        this.port = port;
    }

    public ChatServer() {
        this(DEFAULT_PORT);
    }

    public boolean readyToExit(String msg) {
        return $EXIT.equals(msg);
    }

    public void start() {
        try {
            channel = ServerSocketChannel.open();
            // 开启非阻塞模式
            channel.configureBlocking(false);
            // 得到与channel相关的ServerSocket并绑定端口
            channel.socket().bind(new InetSocketAddress(port));
            selector = Selector.open();
            // 让selector监控channel上的accept事件
            channel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                // 返回 监控到的所有通道上触发的事件 的个数
                // 如果一个也没触发， 就阻塞
                selector.select();
                // 获取被触发事件的相关信息
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    handle(key);
                }
                // 要清空， 不然下一次循环还有这一次的数据
                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void handle(SelectionKey key) throws IOException {
        // 如果是accept事件(和客户端建立了连接)被触发
        if(key.isAcceptable())
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
    }

    public String getClientText(Socket socket)
    {
        // ip:port
        return socket.getInetAddress().getHostAddress()
                + ":" + socket.getPort();
    }

    public void close() {
        try {
            if (selector != null) {
                selector.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}
