package bekyiu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: wangyc
 * @Date: 2020/10/3 13:44
 */
@Data
@NoArgsConstructor
public class User
{
    // hb要求实体中有字段做唯一区分 对应 表中的唯一字段
    private Long id;
    private String name;
    private BigDecimal salary;
    private Date hireDate;
}
