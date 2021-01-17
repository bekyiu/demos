package bekyiu.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: wangyc
 * @Date: 2021/1/17 15:32
 */
@Data
@ConfigurationProperties(prefix = "pet")
public class Pet
{
    private String name;
    private Integer age;
}
