package bekyiu;

import org.junit.Test;
import utils.JpaUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/10/6 20:21
 */
public class QueryTest
{
    /*
    jpql语句, 和hql差不多
    api也和hibernate差不多
     */
    @Test
    public void query()
    {
        String jpql = "SELECT c FROM Customer c ORDER BY c.custId DESC";
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
        List<Customer> list = query.getResultList();
        list.forEach(System.out :: println);

        tx.commit();
    }
}
