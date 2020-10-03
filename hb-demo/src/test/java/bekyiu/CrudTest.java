package bekyiu;

import bekyiu.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: wangyc
 * @Date: 2020/10/3 14:06
 */

public class CrudTest
{
    private Session session;

    @Before
    public void build()
    {
        Configuration config = new Configuration();
        config.configure();

        SessionFactory sessionFactory = config.buildSessionFactory();
        this.session = sessionFactory.openSession();
    }

    @Test
    public void get()
    {
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, 1L);
        // 会发送sql同步数据
        user.setSalary(BigDecimal.TEN);
        System.out.println(user);
        tx.commit();
    }

    @Test
    public void update()
    {
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, 1L);
        user.setHireDate(new Date());
        session.update(user);
        tx.commit();
    }

    @Test
    public void save()
    {
        Transaction tx = session.beginTransaction();
        User u = new User();
        u.setId(61437L);
        u.setName("77");
        u.setHireDate(new Date());
        session.saveOrUpdate(u);
        tx.commit();
    }
}
