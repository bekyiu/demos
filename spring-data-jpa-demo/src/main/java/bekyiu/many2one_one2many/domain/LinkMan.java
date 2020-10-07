package bekyiu.many2one_one2many.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author: wangyc
 * @Date: 2020/10/7 20:44
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cst_linkman")
public class LinkMan
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lkm_id")
    private Long lkmId; //联系人编号(主键)
    @Column(name = "lkm_name")
    private String lkmName;//联系人姓名
    @Column(name = "lkm_gender")
    private String lkmGender;//联系人性别
    @Column(name = "lkm_phone")
    private String lkmPhone;//联系人办公电话
    @Column(name = "lkm_mobile")
    private String lkmMobile;//联系人手机
    @Column(name = "lkm_email")
    private String lkmEmail;//联系人邮箱
    @Column(name = "lkm_position")
    private String lkmPosition;//联系人职位
    @Column(name = "lkm_memo")
    private String lkmMemo;//联系人备注

    /**
     * 多个联系人对应一个客户
     * 类比xml:
     * 	<class name="Employee">
     * 		<id name="id">
     * 			<generator class="native"/>
     * 		</id>
     * 		<property name="name"/>
     * 		<many-to-one name="dept" column="dept_id" class="Department"/>
     * 	</class>
     */
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "lkm_cust_id", referencedColumnName = "cust_id")
    private Customer customer;
}
