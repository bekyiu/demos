package bekyiu.listen;

import bekyiu.taskqueue.TaskQueueServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @Author: wangyc
 * @Date: 2020/10/22 21:54
 */
public class Server
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
                System.out.println(ch);
            }
        });
        // 异步绑定端口号
        ChannelFuture cf = bootstrap.bind(7777);
        // 监听端口号是否绑定成功
        cf.addListener(new GenericFutureListener<Future<? super Void>>()
        {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception
            {
                if (future.isSuccess())
                {
                    System.out.println("绑定端口号成功");
                }
                else
                {
                    System.out.println("绑定端口号失败");
                }
            }
        });
        System.out.println("server started...");
        cf.channel().closeFuture().sync();
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }
}
