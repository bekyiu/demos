package bekyiu.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author: wangyc
 * @Date: 2020/10/22 22:22
 */
public class ClientHandler extends ChannelInboundHandlerAdapter
{
    // 连接建立好
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务端 " + ctx.channel().remoteAddress() + ": " + buf.toString(CharsetUtil.UTF_8));
    }
}
