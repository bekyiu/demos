package bekyiu.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: wangyc
 * @Date: 2020/10/24 11:41
 */
public class HttpServer
{
    public static void main(String[] args)
    {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try
        {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ClientChannelInitializer());
            ChannelFuture cf = bootstrap.bind(7777).sync();
            System.out.println("server started...");
            cf.channel().closeFuture().sync();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
