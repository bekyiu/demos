package bekyiu.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

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
    private List<String> eyes;
    private Map<String, String> skills;
}
