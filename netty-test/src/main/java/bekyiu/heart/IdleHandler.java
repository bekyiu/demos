package bekyiu.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author: wangyc
 * @Date: 2020/10/24 22:20
 */
public class IdleHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        if(evt instanceof IdleStateEvent)
        {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state())
            {
                case ALL_IDLE:
                    // 是否需要心跳检测客户端存活? 或者直接关闭 ctx.close();
                    System.out.println("都空闲");
                    break;
                case READER_IDLE:
                    System.out.println("读空闲");
                    break;
                case WRITER_IDLE:
                    System.out.println("写空闲");
                    break;
            }
        }
    }
}
