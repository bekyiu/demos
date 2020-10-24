package bekyiu.taskqueue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyc
 * @Date: 2020/10/24 10:41
 */
public class TaskQueueServerHandler extends ChannelInboundHandlerAdapter
{

    /*
    如果这里要处理一个耗时很长的任务
    可以把这个任务丢到对应channel所在线程的任务队列里 异步执行
    任务队列里的任务都是单线程执行的
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ctx.channel().eventLoop().execute(() -> {
            try
            {
                TimeUnit.SECONDS.sleep(7);
                System.out.println("异步任务执行完毕");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        });
        System.out.println("channel Read end...");
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(Unpooled.copiedBuffer("zzzz", CharsetUtil.UTF_8));
        System.out.println("channelReadComplete end...");
    }

    // 发生异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        // 关闭通道
        ctx.close();
    }
}
