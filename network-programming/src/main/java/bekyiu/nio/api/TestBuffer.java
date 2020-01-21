package bekyiu.nio.api;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * buffer本质上就是一个数组, 不同类型的buffer存不同类型的数据, 比如有ByteBuffer, IntBuffer, DoubleBuffer...
 * 但是没有BooleanBuffer
 *
 * Buffer类无非也就是提供了一些读写数组的方法
 */
public class TestBuffer
{
    /**
     * 基本用法
     */
    @Test
    public void test1()
    {
        ByteBuffer b = ByteBuffer.allocate(1024);
        // 刚刚分配好的buffer是写模式
        showBufferInfo("allocate", b); // 0 1024 1024
        String s = "abcde"; // 5个byte
        b.put(s.getBytes());
        showBufferInfo("put", b); // 5 1024 1024
        // 切换到读模式
        b.flip();
        showBufferInfo("flip", b); // 0 5 1024
        // 读取数据
        byte[] dst = new byte[b.limit()];
        b.get(dst);
        System.out.println(new String(dst));
        showBufferInfo("get", b); // 5 5 1024
        // 回到写模式
        b.clear();
        showBufferInfo("clear", b); // 0 1024 1024
    }


    /**
     * mark字段
     */
    @Test
    public void test2()
    {
        ByteBuffer b = ByteBuffer.allocate(1024);
        String s = "abcde"; // 5个byte
        b.put(s.getBytes());
        showBufferInfo("put", b); // 5 1024 1024
        b.flip();
        byte[] dst = new byte[b.limit()];
        b.get(dst, 0, 2); // 读两个字节存到dst中
        showBufferInfo("get", b); // 2 5 1024
        // 记录当前position的位置, 方便之后回滚
        b.mark(); // 2
        b.get(dst, 2, 2); // 继续读两个字节存到dst中
        showBufferInfo("get", b); // 4 5 1024
        b.reset(); // position回滚
        showBufferInfo("reset", b); // 2 5 1024

        // 缓冲区还有没有可以读的
        if(b.hasRemaining())
        {
            // 如果有, 还能读几个
            System.out.println(b.remaining()); // 3
        }
    }

    private void showBufferInfo(String method, Buffer buffer)
    {
        System.out.println("-----------" + method + "-----------");
        // 指向当前要操作(读/写)的位置
        System.out.println(buffer.position());
        // 在limit及其之后的位置不能被操作
        System.out.println(buffer.limit());
        // buffer的容量
        System.out.println(buffer.capacity());
    }
}
