package bekyiu.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author: wangyc
 * @Date: 2020/10/22 22:05
 */
public class ServerHandler extends ChannelInboundHandlerAdapter
{
    // 读取客户端发来的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端" + ctx.channel().remoteAddress() + ": " + buf.toString(CharsetUtil.UTF_8));
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(Unpooled.copiedBuffer("zzzz", CharsetUtil.UTF_8));
    }

    // 发生异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        // 关闭通道
        ctx.close();
    }
}
