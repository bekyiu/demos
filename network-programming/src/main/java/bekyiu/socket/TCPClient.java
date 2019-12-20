package bekyiu.socket;

import java.io.*;
import java.net.Socket;

/**
 * TCP客户端: 向服务器发送连接请求, 发送数据, 读取服务器会写的数据
 */
public class TCPClient
{
    public static void main(String[] args) throws Exception
    {
        // 创建客户端socket对象, 和服务器的ip地址和端口号绑定
        Socket socket = new Socket("127.0.0.1", 8888);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
            String msg = consoleReader.readLine();

            writer.write(msg);
            writer.newLine();
            writer.flush();

            String back = reader.readLine();
            System.out.println("back from server: " + back);

            if ("exit".equals(msg))
            {
                break;
            }
        }

        consoleReader.close();
        reader.close();
        writer.close();
        socket.close();
    }
}
