package bekyiu.bio.client;

import java.io.*;
import java.net.Socket;

public class ChatClient
{
    private static final String SERVER_ADDRESS = "39.106.156.11";
    private static final Integer SERVER_PORT = 6140;
    private static final String $EXIT = "$exit";

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public boolean readyToExit(String msg)
    {
        return $EXIT.equals(msg);
    }

    public void send(String msg) throws IOException
    {
        if (!socket.isOutputShutdown())
        {
            writer.write(msg);
            writer.newLine();
            writer.flush();
        }
    }

    public String receive() throws IOException
    {
        if (!socket.isInputShutdown())
        {
            return reader.readLine();
        }
        return null;
    }

    public void start()
    {
        try
        {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 处理用户输入
            new Thread(new UserInputHandler(this)).start();

            // 读取服务器转发来的消息
            String msg = null;
            while ((msg = receive()) != null)
            {
                System.out.println(msg);
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

    public void close()
    {
        try
        {
            if (writer != null)
            {
                writer.close();
            }
            if (reader != null)
            {
                reader.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        System.out.println("进入聊天室, 输入 $exit 退出");
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }
}
