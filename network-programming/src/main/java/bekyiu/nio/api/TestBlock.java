package bekyiu.nio.api;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// 阻塞式的 nio
public class TestBlock
{
    @Test
    public void client() throws IOException
    {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 10086));
        FileChannel in = FileChannel.open(Paths.get("F:\\comic", "Louise.jpg"), StandardOpenOption.READ);
        ByteBuffer buf = ByteBuffer.allocate(1024);
        while(in.read(buf) != -1)
        {
            buf.flip();
            socketChannel.write(buf);
            buf.clear();
        }
        socketChannel.shutdownOutput();
        // 等待server的响应
        int len = 0;
        while((len = socketChannel.read(buf)) != -1)
        {
            buf.flip();
            System.out.println(new String(buf.array(), 0, len));
            buf.clear();
        }

        in.close();
        socketChannel.close();
    }

    @Test
    public void server() throws IOException
    {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(10086));
        SocketChannel socketChannel = serverSocketChannel.accept();
        FileChannel out = FileChannel.open(Paths.get("F:\\", "L.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ByteBuffer buf = ByteBuffer.allocate(1024);
        while(socketChannel.read(buf) != -1)
        {
            buf.flip();
            out.write(buf);
            buf.clear();
        }

        // 响应给客户端
        buf.put("完了".getBytes());
        buf.flip();
        socketChannel.write(buf);

        out.close();
        socketChannel.close();
        serverSocketChannel.close();
    }
}
