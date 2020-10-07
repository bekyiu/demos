package bekyiu.dao;

import bekyiu.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/10/7 11:18
 *
 * JpaRepository<要操作的实体类类型, 实体类中主键的类型>
 * JpaSpecificationExecutor<要操作的实体类类型>
 */
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer>
{
    /**
     * 自定义查询方法
     *
     * value 中写jpql, 占位符后面可以跟索引, 对应于方法的参数, 如果和参数保持一致可以不写
     */
    @Query(value = "SELECT c FROM Customer c WHERE c.custName LIKE ?1 OR custId = ?2")
    List<Customer> getByNameOrId(String name, Long id);

    /**
     * jpql 也可以完成更新
     */
    @Query(value = "UPDATE Customer c SET c.custIndustry = ? WHERE c.custId = ?")
    @Modifying
    int update(String industry, Long id);

    /**
     * 也可以写sql, 占位符参数的设置规则和jpql一样
     */
    @Query(value = "SELECT * FROM cst_customer WHERE cust_name LIKE ? OR cust_id = ?", nativeQuery = true)
    List<Customer> sqlQuery(String name, Long id);

    /**
     * 方法命名规则查询
     * 就是根据方法签名去生成sql
     * findBy + 要查询的属性 + [查询方式(Like | isnull)] + [AND | OR + (要查询的属性...)]
     * 例如 findByCustName -> where cust_name = ?
     * findByCustNameLike -> where cust_name Like ?
     */
    List<Customer> findByCustNameLikeOrCustId(String name, Long id);
}
