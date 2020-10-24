package bekyiu.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @Author: wangyc
 * @Date: 2020/10/24 20:20
 */
public class ByteBufTest
{
    public static void main(String[] args)
    {
        t2();
    }

    public static void t1()
    {
        /*
        netty 提供的数据容器 比nio的buffer更加方便
        ByteBuf 中有三个重要的值
            readerIndex: 下一个要读取的位置
            writerIndex: 要一个要写的位置
            capacity: 数组的大小
        */
        ByteBuf buf = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++)
        {
            buf.writeByte(i);
        }
        for (int i = 0; i < buf.capacity(); i++)
        {
            System.out.println(buf.readByte());
        }
    }

    public static void t2()
    {
        ByteBuf buf = Unpooled.copiedBuffer("nishino nanase", Charset.forName("utf-8"));
        System.out.println(buf);
        if(buf.hasArray())
        {
            byte[] bytes = buf.array();
            System.out.println(new String(bytes, Charset.forName("utf-8")));
        }
    }

}
