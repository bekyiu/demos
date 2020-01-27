package bekyiu.aio.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Client
{

    private AsynchronousSocketChannel clientChannel;
    public void start()
    {
        try
        {
            clientChannel = AsynchronousSocketChannel.open();
            // 建立连接 一律采用future的方式
            Future<Void> connect = clientChannel.connect(new InetSocketAddress("127.0.0.1", 7777));
            connect.get();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(true)
            {
                String input = br.readLine();
                // 这样创建出来的buffer是读模式的, pos为0, 可以debug观察
                ByteBuffer buffer = ByteBuffer.wrap(input.getBytes());

                Future<Integer> write = clientChannel.write(buffer);

                write.get();
                buffer.clear();

                Future<Integer> read = clientChannel.read(buffer);
                read.get();

                System.out.println(new String(buffer.array()));
            }
        }
        catch (IOException | InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Client client = new Client();
        client.start();
    }
}
