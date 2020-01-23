package bekyiu.nio.api;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class TestNoBlock {
    @Test
    public void server() throws Exception {
        List<SocketChannel> list = new ArrayList<>();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(6666));

        while (true) {
            for (SocketChannel socket : list) {
                ByteBuffer buf = ByteBuffer.allocate(1024);
                // 不阻塞
                int read = socket.read(buf);
                if(read > 0)
                {
                    buf.flip();
                    System.out.println(new String(buf.array(), 0, buf.limit()));
                    buf.clear();
                }
            }

            // 不阻塞
            SocketChannel accept = server.accept();
            if(accept != null)
            {
                accept.configureBlocking(false);
                list.add(accept);
            }
        }
    }

    @Test
    public void client() throws Exception
    {
        Socket socket = new Socket("127.0.0.1", 6666);
        Thread.sleep(1000 * 5);
        socket.getOutputStream().write("1".getBytes());
        socket.close();
    }
    @Test
    public void client2() throws Exception
    {
        Socket socket = new Socket("127.0.0.1", 6666);
        socket.getOutputStream().write("2".getBytes());
        socket.close();
    }
}

