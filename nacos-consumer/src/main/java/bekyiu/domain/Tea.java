package bekyiu.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: wangyc
 * @Date: 2020/9/10 14:57
 */
@Data
@Builder
public class Tea
{
    private Long id;
    private String name;
}
