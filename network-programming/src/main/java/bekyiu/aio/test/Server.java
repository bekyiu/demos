package bekyiu.aio.test;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.Map;

public class Server
{
    private AsynchronousServerSocketChannel serverChannel;

    private void close(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void start()
    {
        try
        {
            serverChannel = AsynchronousServerSocketChannel.open();
            // 绑定端口
            serverChannel.bind(new InetSocketAddress("127.0.0.1", 7777));
            System.out.println("server启动...");
            while (true)
            {
                // 这个accept是异步调用, 返回的结果在AcceptHandler的回调函数中处理
                // attachment表示可以传递一些附加信息给回调函数
                serverChannel.accept(null, new AcceptHandler());
                // 阻塞 不让主线程结束
                System.in.read();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Server server = new Server();
        server.start();
    }

    // 第一个泛型表示, 异步方法应该返回什么, 第二个泛型是attachment的类型
    private class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object>
    {
        // 成功返回时, 执行的回调
        @Override
        public void completed(AsynchronousSocketChannel result, Object attachment)
        {
            if(serverChannel.isOpen())
            {
                serverChannel.accept(null, this);
            }
            // 拿到和客户端交互的socket
            AsynchronousSocketChannel clientChannel = result;
            // 读客户端发送来的消息
            if(clientChannel != null && clientChannel.isOpen())
            {
                ClientHandler handler = new ClientHandler(clientChannel);

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                Map<String, Object> info = new HashMap<>();
                info.put("type", "read");
                info.put("buffer", buffer);
                clientChannel.read(buffer, info, handler);
            }
        }

        // 返回失败时, 执行的回调
        @Override
        public void failed(Throwable exc, Object attachment)
        {
            System.out.println(exc);
            System.out.println(attachment);
        }
    }

    private class ClientHandler implements CompletionHandler<Integer, Object>
    {
        private AsynchronousSocketChannel clientChannel;

        public ClientHandler(AsynchronousSocketChannel clientChannel)
        {
            this.clientChannel = clientChannel;
        }

        @Override
        public void completed(Integer result, Object attachment)
        {
            Map<String, Object> info = (Map<String, Object>) attachment;
            String type = (String) info.get("type");
            if("read".equals(type))
            {
                info.put("type", "write");
                ByteBuffer buffer = (ByteBuffer) info.get("buffer");
                buffer.flip();
                clientChannel.write(buffer, info, this);
                buffer.clear();
            }
            else if("write".equals(type))
            {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                info.put("type", "read");
                info.put("buffer", buffer);
                clientChannel.read(buffer, info, this);
            }
        }

        @Override
        public void failed(Throwable exc, Object attachment)
        {
            System.out.println(exc);
            System.out.println(attachment);
        }
    }
}
