package bekyiu.nio.api;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestChannel
{

    // 直接缓冲区
    @Test
    public void nioCopy3() throws Exception
    {
        FileChannel in = FileChannel.open(Paths.get("F:\\comic\\", "l.jpg"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("F:\\", "l.jpg"), StandardOpenOption.WRITE,
                StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 相当于 把F:/comic/l.jpg这个文件映射到inmap这块内存中
        MappedByteBuffer inmap = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
        // 相当于 把F:/l.jpg这个文件映射到outmap这块内存中
        MappedByteBuffer outmap = out.map(FileChannel.MapMode.READ_WRITE, 0, in.size());
        byte[] b = new byte[inmap.limit()];
        inmap.get(b);
        outmap.put(b);
    }

    // 分散读取, 聚集写入
    @Test
    public void t1() throws Exception
    {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");
        // 1.获取通道
        FileChannel ch1 = raf1.getChannel();

        // 2.分散读取, 指从通道读取数据, 按顺序写入到多个buffer中
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(100);
        ByteBuffer buf3 = ByteBuffer.allocate(4096);

        ByteBuffer[] bufs = {buf1, buf2, buf3};
        ch1.read(bufs);

        // 观察一下bufs中的数据, 看看是否果真如此
        for (ByteBuffer buf : bufs)
        {
            // 切换为读模式
            buf.flip();
            System.out.println(new String(buf.array(), 0, buf.limit()));
        }

        // 3.聚集写入, 指从多个buffer中按顺序读出, 写入到同一个通道
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel ch2 = raf2.getChannel();
        ch2.write(bufs);
    }

    @Test
    public void t2()
    {

    }
}
