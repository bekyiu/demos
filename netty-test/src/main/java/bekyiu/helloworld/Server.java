package bekyiu.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: wangyc
 * @Date: 2020/10/22 21:54
 */
public class Server
{
    public static void main(String[] args) throws InterruptedException
    {
        /*
        两个线程池
        boss只处理建立连接的请求 业务交给work
        线程数量 = cpu核数 * 2
         */
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
                ch.pipeline().addLast(new ServerHandler());
            }
        });

        ChannelFuture cf = bootstrap.bind(7777).sync();
        System.out.println("server started...");
        cf.channel().closeFuture().sync();
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }
}
