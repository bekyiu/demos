package bekyiu;

import org.junit.Test;
import utils.JpaUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @Author: wangyc
 * @Date: 2020/10/6 16:18
 */
public class CrudTest
{
    @Test
    public void testSave()
    {
        // 1.加载配置文件创建工厂（实体管理器工厂 类比 SessionFactory）对象  是线程安全的
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        // 2.通过实体管理器工厂获取实体管理器 (类比session) 是线程不安全的
        EntityManager em = factory.createEntityManager();
        // 3.获取事务对象，开启事务
        EntityTransaction tx = em.getTransaction();
        // 开启事务
        tx.begin();
        // 4.完成增删改查操作
        Customer customer = new Customer();
        customer.setCustName("nanase");
        customer.setCustIndustry("famous actress");
        // 保存
        em.persist(customer);
        // 5.提交事务
        tx.commit();
        // 6.释放资源
        em.close();
        factory.close();
    }

    @Test
    public void find()
    {
        EntityManager m = JpaUtils.getEntityManager();
        EntityTransaction tx = m.getTransaction();
        tx.begin();
        // 相当于hibernate的get, 查询出来的就是Customer对象
        Customer c = m.find(Customer.class, 614L);
        System.out.println(c);
        tx.commit();
    }

    @Test
    public void ref()
    {
        EntityManager m = JpaUtils.getEntityManager();
        EntityTransaction tx = m.getTransaction();
        tx.begin();
        // 延迟加载, 相当于hibernate的load, 查询出来的是代理对象
        Customer c = m.getReference(Customer.class, 614L);
        System.out.println(c);
        tx.commit();
    }

    @Test
    public void remove()
    {
        EntityManager m = JpaUtils.getEntityManager();
        EntityTransaction tx = m.getTransaction();
        tx.begin();
        Customer c = new Customer();
        c.setCustId(614L);
        /*
        相当于hibernate的delete
        不能直接删除: Removing a detached instance
         */
        m.remove(m.merge(c));
        tx.commit();
    }

    @Test
    public void merge()
    {
        EntityManager m = JpaUtils.getEntityManager();
        EntityTransaction tx = m.getTransaction();
        tx.begin();
        // 相当于hibernate的saveOrUpdate
        Customer c = m.find(Customer.class, 614L);
        c.setCustIndustry("actress");
        c.setCustAddress("japan");
        m.merge(c);
        tx.commit();
    }
}
