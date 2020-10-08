package bekyiu.many2many.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: wangyc
 * @Date: 2020/10/7 20:44
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "sys_role")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "role_name")
    private String roleName;

    /**
     * 放弃维护关系
     * 多对多中, 一般来说, 谁是被选择的, 谁就放弃维护关系
     * mappedBy取值为对方关系的属性名称
     */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}
