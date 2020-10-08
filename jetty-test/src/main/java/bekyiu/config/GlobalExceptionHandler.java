package bekyiu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: wangyc
 * @Date: 2020/9/1 21:13
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String all(Exception ex)
    {
        log.error("===========1=============");
        ex.printStackTrace();
        return "all";
    }

    @ResponseBody
    @ExceptionHandler(value = ArithmeticException.class)
    public String zero(ArithmeticException ex)
    {
        log.error("===========2=============");
        log.error(ex.getMessage());
        return "by zero";
    }
}
