package bekyiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: wangyc
 * @Date: 2020/7/25 10:35
 */
@SpringBootApplication

// 通用mapper的注解，在mapper接口上用mybatis的@Mapper注解也可以
@MapperScan("bekyiu.mapper")
public class TkMapper
{
    public static void main(String[] args)
    {
        SpringApplication.run(TkMapper.class, args);
    }
}
