package bekyiu.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CORSInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
    {
        // 跨域
        String origin = req.getHeader("Origin");
        if (origin == null)
        {
            resp.addHeader("Access-Control-Allow-Origin", "*");
        }
        else
        {
            resp.addHeader("Access-Control-Allow-Origin", origin);
        }
        resp.addHeader("Access-Control-Allow-Headers", "Origin, x-requested-with, Content-Type, Accept,X-Cookie");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");
        if (req.getMethod().equals("OPTIONS"))
        {
            System.out.println("options");
            resp.setStatus(HttpServletResponse.SC_OK);
            return false;
        }
        return true;
    }
}
