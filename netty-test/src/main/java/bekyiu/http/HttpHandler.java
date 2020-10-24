package bekyiu.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Author: wangyc
 * @Date: 2020/10/24 11:41
 *
 * 每次http请求用的都是不同的HttpHandler
 */
public class HttpHandler extends SimpleChannelInboundHandler<HttpObject>
{
    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception
    {
        if(msg instanceof HttpRequest)
        {
            DefaultHttpRequest req = (DefaultHttpRequest) msg;
            if(req.uri().equals("/favicon.ico"))
            {
                // 过滤不需要的请求
                return;
            }
            // 回复信息
            ByteBuf buf = Unpooled.copiedBuffer("hahah im server", CharsetUtil.UTF_8);
            // 构造http响应
            DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, buf);
            resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
                    .set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
            // 发送
            ctx.writeAndFlush(resp);
        }
    }
}
