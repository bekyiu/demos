package bekyiu.config;

import bekyiu.interceptor.CORSInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer
{
    @Autowired
    private CORSInterceptor corsInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(corsInterceptor).addPathPatterns("/hello");
    }
}
