package bekyiu.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP 服务端
 */
public class TCPServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket server = new ServerSocket(8888);
        Socket socket = server.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        System.out.println("port: " + socket.getPort() + "连接上了服务器");

        String msg = null;
        while ((msg = reader.readLine()) != null)
        {
            System.out.println("收到" + socket.getPort() + "的消息: " + msg);
            writer.write("back " + msg);
            writer.newLine();
            writer.flush();
        }


        socket.close(); // socket直接关闭, client的read当来就读不到数据了
        server.close();
        reader.close();
        writer.close();
    }

}
