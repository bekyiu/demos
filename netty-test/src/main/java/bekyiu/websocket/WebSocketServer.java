package bekyiu.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: wangyc
 * @Date: 2020/10/25 10:54
 */
public class WebSocketServer
{
    public static void main(String[] args) throws InterruptedException
    {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>()
        {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception
            {
                // WebSocket握手是基于http的
                ch.pipeline().addLast(new HttpServerCodec())
                        // 对写大数据流的支持
                        .addLast(new ChunkedWriteHandler())
                        // 聚合http消息
                        .addLast(new HttpObjectAggregator(8192))
                        // 支持WebSocket
                        .addLast(new WebSocketServerProtocolHandler("/hello"))
                        .addLast(new TextWebSocketFrameHandler());
            }
        });
        ChannelFuture cf = bootstrap.bind(7777).sync();
        System.out.println("server started...");
        cf.channel().closeFuture().sync();
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }
}
