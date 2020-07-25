package bekyiu.domain;

import bekyiu.enums.Sex;
import bekyiu.typehandler.SexTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.*;

/**
 * @Author: wangyc
 * @Date: 2020/7/25 11:33
 */
@Data
//@Builder
// orm，表名和实体类的映射
@Table(name = "user")
public class User
{

    // 标明主键
    @Id
    // 自增长主键回写
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 字段名和列名的映射
    @Column(name = "name")
    private String username;
    private String password;
    // 枚举用这个处理
    @ColumnType(typeHandler = SexTypeHandler.class)
    private Sex sex;

    // 非表中的字段 不要和@Builder混用
//     @Transient
//     private Boolean isShow;
//
//    public User()
//    {
//    }
}
