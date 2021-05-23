package bekyiu.nio.api;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class TestSelector {
    @Test
    public void server() throws IOException {
        // 监听有事件发生的channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(9999));
        // 将ssc注册到selector, 让其监听ssc上的accept事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 无事件发生会阻塞, 如果有没处理的事件select则不会阻塞
            selector.select();
            // 返回发生的事件
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key : keys) {
                try {
                    handle(key, selector);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 发生异常就取消掉该事件, 下载不再执行
                    key.cancel();
                }
            }
            // 清空已处理过的事件, 或用迭代器的方式, 处理一个删除一个
            // 如果不清空但是事件实际上又被处理了, 那么在下一轮循环中一定会出问题
            keys.clear();
        }
    }

    private void handle(SelectionKey key, Selector sel) throws IOException {
        System.out.println("TestSelector.handle");
        // 判断事件类型
        if (key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel ch = ssc.accept();
            ch.configureBlocking(false);
            ch.register(sel, SelectionKey.OP_READ);
        } else if(key.isReadable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            int len = sc.read(buf);
            if (len == -1) {
                key.cancel();
            }
            buf.flip();
            System.out.println(new String(buf.array(), StandardCharsets.UTF_8));
        }
    }


    @Test
    public void client() throws IOException, InterruptedException {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        sc.write(ByteBuffer.wrap("aaa".getBytes(StandardCharsets.UTF_8)));
        Thread.sleep(20000);
    }
}
