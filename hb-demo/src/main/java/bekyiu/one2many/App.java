package bekyiu.one2many;

import bekyiu.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class App
{
    @Before
    public void save()
    {
        Employee e1 = new Employee();
        Employee e2 = new Employee();
        Department d = new Department();

        e1.setName("a");
        e2.setName("b");
        d.setDeptName("develop");
        d.getList().add(e1);
        d.getList().add(e2);

        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(d);
        session.save(e1);
        session.save(e2);

        transaction.commit();
        session.close();

    }

    @Test
    public void get()
    {
        System.out.println("------------------------");
        Session session = HibernateUtil.openSession();
        Department d = session.get(Department.class, 1L);
        System.out.println("d = " + d);
        // 延迟加载
        System.out.println(d.getList());
        session.close();
    }
}
