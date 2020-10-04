package bekyiu.many2one;

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
        Employee e3 = new Employee();

        Department d = new Department();

        e1.setName("a");
        e2.setName("b");
        e3.setName("c");
//        e1.setDept(d);
        e2.setDept(d);
        e3.setDept(d);


        d.setDeptName("develop");


        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();

        // 先保存one方
        session.save(d);
        session.save(e1);
        session.save(e2);
        session.save(e3);

        transaction.commit();
        session.close();
    }

    @Test
    public void get()
    {
        System.out.println("---------------------");
        Session session = HibernateUtil.openSession();
        Employee e = session.get(Employee.class, 1L);
        Department dept = e.getDept();
        if(dept == null)
        {
            System.out.println("nulllll");
        }
        System.out.println("e = " + e);
        // 延迟加载
        System.out.println(dept);
        session.close();
    }
}
