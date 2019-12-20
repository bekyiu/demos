package bekyiu.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatHandler implements Runnable
{
    private ChatServer chatServer;
    private Socket socket;

    public ChatHandler(ChatServer chatServer, Socket socket)
    {
        this.chatServer = chatServer;
        this.socket = socket;
    }


    @Override
    public void run()
    {
        BufferedReader reader = null;
        try
        {
            chatServer.addClient(socket);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = null;
            while ((msg = reader.readLine()) != null)
            {
                // 注意加一个 \n, 因为readLine是会读到一个分隔符停止
                String fwdMsg = "[" + chatServer.getClientText(socket) + "]: " + msg + "\n";
                System.out.print(fwdMsg);
                chatServer.forwardMessage(socket, fwdMsg);
                if (chatServer.readyToExit(msg))
                {
                    break;
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                chatServer.removeClient(socket);
                socket.close();
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
    }
}
