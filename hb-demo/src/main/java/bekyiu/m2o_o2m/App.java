package bekyiu.m2o_o2m;

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
        d.getEmployees().add(e1);
        d.getEmployees().add(e2);

        e1.setDept(d);
        e2.setDept(d);

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
        System.out.println("---------------------");
    }
}
