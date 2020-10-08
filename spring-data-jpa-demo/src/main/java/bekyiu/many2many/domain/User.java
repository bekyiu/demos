package bekyiu.many2many.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户和角色是多对多的关系
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "sys_user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column
    private String username;
    @Column
    private Integer age;

    /**
     * 类比xml:
     * 	<class name="Teacher">
     * 		<id name="id">
     * 			<generator class="native"/>
     * 		</id>
     * 		<property name="name"/>
     * 		<!--table写中间表-->
     * 		<bag name="students" table="students_teachers">
     * 			<!--自己的id在中间表中的名称-->
     * 			<key column="t_id"/>
     * 			<many-to-many class="Student" column="s_id"/>
     * 		</bag>
     * 	</class>
     */
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(
            // 中间表名称
            name = "sys_user_role",
            // 自己在中间表中的外键列
            joinColumns = {@JoinColumn(name = "sys_user_id", referencedColumnName = "user_id")},
            // 对方在中间表中的外键列
            inverseJoinColumns = {@JoinColumn(name = "sys_role_id", referencedColumnName = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();
}