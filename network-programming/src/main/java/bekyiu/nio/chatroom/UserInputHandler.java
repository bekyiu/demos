package bekyiu.nio.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputHandler implements Runnable
{
    private ChatClient chatClient;

    public UserInputHandler(ChatClient chatClient)
    {
        this.chatClient = chatClient;
    }

    @Override
    public void run()
    {
        BufferedReader consoleReader = null;
        try
        {
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String input = null;
            while (true)
            {
                input = consoleReader.readLine();
                // 发消息
                chatClient.send(input);
                if (chatClient.readyToExit(input))
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
                if (consoleReader != null)
                {
                    consoleReader.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
