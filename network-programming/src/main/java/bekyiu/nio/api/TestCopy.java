package bekyiu.nio.api;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestCopy
{
    public static void main(String[] args)
    {
        // 间接缓冲区
        CopyFile nioCopy1 = ((src, dest) ->
        {
            FileChannel fin = null;
            FileChannel fout = null;

            try
            {
                fin = new FileInputStream(src).getChannel();
                fout = new FileOutputStream(dest).getChannel();
                ByteBuffer buf = ByteBuffer.allocate(1024);
                while (fin.read(buf) != -1)
                {
                    buf.flip();
                    while (buf.hasRemaining())
                    {
                        fout.write(buf);
                    }
                    buf.clear();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                close(fin);
                close(fout);
            }
        });

        // 直接缓冲区
        CopyFile nioCopy2 = ((src, dest) -> {
            FileChannel fin = null;
            FileChannel fout = null;
            try
            {
                fin = new FileInputStream(src).getChannel();
                fout = new FileOutputStream(dest).getChannel();
                long transfered = 0L;
                while(transfered != fin.size())
                {
                    transfered += fin.transferTo(0, fin.size(), fout);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                close(fin);
                close(fout);
            }
        });

        nioCopy2.copy(new File("F:\\comic\\tomcat.jpg"), new File("F:\\tomcat.jpg"));
    }

    public static void close(Closeable c)
    {
        try
        {
            if (c != null)
            {
                c.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
