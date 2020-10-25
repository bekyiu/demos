package bekyiu.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @Author: wangyc
 * @Date: 2020/10/25 11:01
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception
    {
        System.out.println("client: " + msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("server: " + LocalDateTime.now() +
                " " + msg.text()));
    }

    // 当有客户端连接后出发
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("TextWebSocketFrameHandler.handlerAdded: " + ctx.channel().id().asLongText());
        System.out.println("TextWebSocketFrameHandler.handlerAdded: " + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("TextWebSocketFrameHandler.handlerRemoved: " + ctx.channel().id().asLongText());
    }
}
