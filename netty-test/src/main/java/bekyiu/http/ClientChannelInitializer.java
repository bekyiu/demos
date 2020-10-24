package bekyiu.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: wangyc
 * @Date: 2020/10/24 11:44
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ch.pipeline()
                // http的编解码器
                .addLast("httpCodec", new HttpServerCodec())
                .addLast("serverHandler", new HttpHandler());
    }
}
