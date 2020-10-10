package bekyiu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: wangyc
 * @Date: 2020/10/10 19:11
 */
@Configuration
public class WebSocketConfig
{
    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {
        // 解析 @ServerEndpoint
        return new ServerEndpointExporter();
    }
}
