package bekyiu.nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class ChatClient
{
    private static final Integer DEFAULT_PORT = 6140;
    private static final String DEFAULT_SERVER_HOST = "127.0.0.1";
    private static final String $EXIT = "$exit";

    private String host;
    private int port;
    private SocketChannel channel;
    private Selector selector;
    private ByteBuffer rBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer wBuffer = ByteBuffer.allocate(1024);
    private Charset charset = StandardCharsets.UTF_8;

    public ChatClient(String host, int port)
    {
        this.port = port;
        this.host = host;
    }

    public ChatClient()
    {
        this(DEFAULT_SERVER_HOST, DEFAULT_PORT);
    }

    public boolean readyToExit(String msg)
    {
        return $EXIT.equals(msg);
    }

    public void start()
    {
        try
        {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            selector = Selector.open();
            // 向server发起请求建立连接后, SocketChannel会触发connect事件
            channel.register(selector, SelectionKey.OP_CONNECT);
            // 建立连接
            channel.connect(new InetSocketAddress(host, port));
            while (true)
            {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys)
                {
                    handle(key);
                }
                selectionKeys.clear();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClosedSelectorException e)
        {

        }
    }

    private void handle(SelectionKey key) throws IOException
    {
        if (key.isConnectable())
        {
            SocketChannel client = (SocketChannel) key.channel();
            // 有可能连接还没有建立成功
            // 指示此通道上的连接操作是否正在进行
            // 当且仅当此通道上已启动连接操作，但尚未通过调用finishConnect方法完成时，返回:true
            if (client.isConnectionPending())
            {
                client.finishConnect();
                new Thread(new UserInputHandler(this)).start();
            }
            // server 可能有转发过来的消息
            client.register(selector, SelectionKey.OP_READ);
        }
        else if(key.isReadable())
        {
            SocketChannel client = (SocketChannel) key.channel();
            String msg = receive(client);
            System.out.println(msg);
        }
    }

    private String receive(SocketChannel client) throws IOException
    {
        rBuffer.clear();
        while (client.read(rBuffer) > 0);
        rBuffer.flip();
        return String.valueOf(charset.decode(rBuffer));
    }

    public void send(String input) throws IOException
    {
        wBuffer.clear();
        wBuffer.put(charset.encode(input));
        wBuffer.flip();
        channel.write(wBuffer);

        if(readyToExit(input))
        {
            close();
        }
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
        ChatClient client = new ChatClient("127.0.0.1", 7777);
        client.start();
    }
}
