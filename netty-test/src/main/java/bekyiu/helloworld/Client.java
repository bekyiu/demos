package bekyiu.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: wangyc
 * @Date: 2020/10/22 21:54
 */
public class Client
{
    public static void main(String[] args) throws InterruptedException
    {
        EventLoopGroup executors = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(executors);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>()
        {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception
            {
                ch.pipeline().addLast(new ClientHandler());
            }
        });
        System.out.println("client started...");

        ChannelFuture cf = bootstrap.connect("localhost", 7777).sync();
        cf.channel().closeFuture().sync();
        executors.shutdownGracefully();
    }
}
