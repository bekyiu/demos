package bekyiu.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyc
 * @Date: 2020/10/24 22:00
 * <p>
 * 空闲检测机制
 */
public class IdleServer
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
                /*
                IdleStateHandler对象是用来触发IdleStateEvent的
                3s没有读触发
                5s没有写触发
                7s既没有读也没有写触发
                触发的事件会传递到下一个handler
                 */
                ch.pipeline().addLast(new IdleStateHandler(3,
                        5, 7, TimeUnit.SECONDS))
                        .addLast(new IdleHandler());
            }
        });
        // 异步绑定端口号
        ChannelFuture cf = bootstrap.bind(7777).sync();
        System.out.println("server started...");
        cf.channel().closeFuture().sync();
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }
}
