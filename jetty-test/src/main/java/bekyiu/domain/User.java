package bekyiu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: wangyc
 * @Date: 2020/10/8 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User
{
    private Long id;
    private String username;
    private String realName;
    private Date hireDate;
    private String phone;
    private Boolean status;
}
