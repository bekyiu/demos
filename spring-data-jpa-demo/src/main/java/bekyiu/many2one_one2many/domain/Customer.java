package bekyiu.many2one_one2many.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户, 相当于一个公司
 * 联系人, 相当于公司的员工
 * 客户和联系人是一对多的关系
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cst_customer")
public class Customer
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId; //客户的主键

    @Column(name = "cust_name")
    private String custName;//客户名称

    @Column(name = "cust_source")
    private String custSource;//客户来源

    @Column(name = "cust_level")
    private String custLevel;//客户级别

    @Column(name = "cust_industry")
    private String custIndustry;//客户所属行业

    @Column(name = "cust_phone")
    private String custPhone;//客户的联系方式

    @Column(name = "cust_address")
    private String custAddress;//客户地址

    /**
     * 一个客户对应多个联系人
     * @OneToMany:
     *      targetEntity: 表示many方的字节码类型
     * @JoinColumn:
     *      name: one方的id在many方作为外键的名称
     *      referencedColumnName: 这个外键源自one方的哪个列
     *
     * 这两个注解可类比one 2 many的xml配置:
     * 	<class name="Department">
     * 		<id name="id">
     * 			<generator class="native"/>
     * 		</id>
     * 		<property name="deptName"/>
     * 		<bag name="list">
     * 			<!--one方的主键id 在 many方作为外键的名称-->
     * 			<key column="dept_id"/>
     * 			<one-to-many class="Employee" />
     * 		</bag>
     * 	</class>
     * 	@OneToMany(targetEntity = LinkMan.class)
     *  @JoinColumn(name = "lkm_cust_id", referencedColumnName = "cust_id")
     *  以上就是正常的one2many的配置
     *
     *  但是这里配置的是双向对多一的关系, 所以需要一方放弃维护关系
     *  所以@OneToMany(mappedBy = "customer"): 参数表示对方配置关系的属性名称
     */
    @OneToMany(mappedBy = "customer")
    private Set<LinkMan> linkMan = new HashSet<>();
}