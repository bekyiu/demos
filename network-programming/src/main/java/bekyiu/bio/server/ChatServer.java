package bekyiu.bio.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ChatServer
{
    private static final Integer DEFAULT_PORT = 6140;
    private static final String $EXIT = "$exit";

    // String是 ip:port, 代表一个客户端, 向这个客户端写东西时, 使用对应的writer
    private Map<String, Writer> connectClients = new HashMap<>();
    private ServerSocket serverSocket;
    private ExecutorService executorService = new ThreadPoolExecutor(10,
            10, 1, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(3));

    public boolean readyToExit(String msg)
    {
        return $EXIT.equals(msg);
    }

    public String getClientText(Socket socket)
    {
        // ip:port
        return socket.getInetAddress().getHostAddress()
                + ":" + socket.getPort();
    }

    public synchronized void addClient(Socket socket) throws IOException
    {
        if (socket != null)
        {
            String client = getClientText(socket);
            connectClients.put(client,
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            System.out.println("客户端[" + client + "]进入聊天室...");
        }
    }

    public synchronized void removeClient(Socket socket) throws IOException
    {
        if (socket != null)
        {
            String client = getClientText(socket);
            if (connectClients.containsKey(client))
            {
                connectClients.remove(client).close();
            }
            System.out.println("客户端[" + client + "]已下线...");
        }
    }

    /**
     * 向其他客户端转发消息
     *
     * @param socket
     * @param fwdMsg
     */
    public synchronized void forwardMessage(Socket socket, String fwdMsg) throws IOException
    {
        if (socket != null)
        {
            String client = getClientText(socket);
            for (String c : connectClients.keySet())
            {
                if (!c.equals(client))
                {
                    Writer w = connectClients.get(c);
                    w.write(fwdMsg);
                    w.flush();
                }
            }
        }
    }

    public void start()
    {
        try
        {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("服务器启动, 监听端口: " + DEFAULT_PORT);
            while (true)
            {
                Socket socket = serverSocket.accept();
                // 创建ChatHandler线程
                // new Thread(new ChatHandler(this, socket)).start();
                executorService.execute(new ChatHandler(this, socket));
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

    public synchronized void close()
    {
        try
        {
            if (serverSocket != null)
            {
                serverSocket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}
