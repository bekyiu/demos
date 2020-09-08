package bekyiu.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: wangyc
 * @Date: 2020/9/8 20:37
 */
@Data
@Builder
public class Student
{
    private Long id;
    private String name;
    private Integer age;
}
