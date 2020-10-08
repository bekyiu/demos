package bekyiu;

import bekyiu.dao.CustomerDao;
import bekyiu.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;

/**
 * @Author: wangyc
 * @Date: 2020/10/7 15:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class QueryTest
{
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void t1()
    {
        customerDao.getByNameOrId("%an%", 2L).forEach(System.out :: println);
    }

    @Test
    // 需要事务才能执行
    @Transactional
    // junit的事务执行完之后默认是要回滚的, 设置不回滚
    @Rollback(false)
    public void t2()
    {
        System.out.println(customerDao.update("actress", 1L));
    }

    @Test
    public void t3()
    {
        customerDao.sqlQuery("%nana%", 2L).forEach(System.out :: println);
    }

    @Test
    public void t4()
    {
        customerDao.findByCustNameLikeOrCustId("%nana%", 2L).forEach(System.out :: println);
    }

    //-------------- 动态查询 -----------------
    // 查询id = 1的对象
    @Test
    public void t5()
    {
        Customer c = customerDao.findOne(new Specification<Customer>()
        {
            /**
             * 动态拼接查询条件
             * @param root 获取需要查询的对象属性
             * @param query 不用
             * @param cb 构造查询条件的，内部封装了很多的查询条件（模糊匹配，精准匹配）
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb)
            {
                // 获取要参与查询的属性
                Path<Object> custId = root.get("custId");
                // 构造查询条件
                Predicate p = cb.equal(custId, 1L);
                return p;
            }
        });

        System.out.println(c);
    }

    // 多条件查询 且按id倒序排序
    @Test
    public void t6()
    {
        customerDao.findAll((root, query, cb) ->
        {
            Path<Object> custName = root.get("custName");
            Path<Object> custId = root.get("custId");
            // equal可以直接比较, 其他的例如like, gt等需要指定类型
            Predicate p1 = cb.like(custName.as(String.class), "%nana%");
            Predicate p2 = cb.equal(custId, 2L);
            return cb.or(p1, p2);
        }, new Sort(Sort.Direction.DESC, "custId")).forEach(System.out :: println);
    }

    // 分页
    @Test
    public void t7()
    {
        // 当前第几页(从0开始), 每页多少条
        Pageable pageable = new PageRequest(0, 2);
        Page<Customer> page = customerDao.findAll(pageable);
        // 内容
        System.out.println(page.getContent());
        // 总条数
        System.out.println("总条数: " + page.getTotalElements());
        // 总页数
        System.out.println("总页数: " + page.getTotalPages());
    }
}
