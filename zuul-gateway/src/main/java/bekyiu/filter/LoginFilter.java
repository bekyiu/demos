package bekyiu.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginFilter extends ZuulFilter
{
    // 在什么时机做过滤
    @Override
    public String filterType()
    {
        return "pre";
    }

    // 过滤器优先级, 越小越有限
    @Override
    public int filterOrder()
    {
        return 100;
    }

    // 过滤器是否生效
    @Override
    public boolean shouldFilter()
    {
        return true;
    }

    // 过滤逻辑
    @Override
    public Object run() throws ZuulException
    {
        // 获取zuul提供的上下文
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest req = context.getRequest();
        String token = req.getParameter("token");
        if(StringUtils.isBlank(token))
        {
            // 过滤请求, 不对其进行路由
            context.setSendZuulResponse(false);
            // 设置相应状态码 401
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            // 提示信息
            context.setResponseBody("UNAUTHORIZED");
        }
        // 校验通过，把登陆信息放入上下文信息，继续向后执行
        context.set("token", token);
        return null;
    }
}
