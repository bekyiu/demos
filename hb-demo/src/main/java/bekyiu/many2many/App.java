package bekyiu.many2many;

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
        Teacher t1 = new Teacher();
        t1.setName("Louise");
        Teacher t2 = new Teacher();
        t2.setName("yukino");

        Student s1 = new Student();
        s1.setName("a");
        Student s2 = new Student();
        s2.setName("b");

        t1.getStudents().add(s1);
        t1.getStudents().add(s2);
        t2.getStudents().add(s1);
        t2.getStudents().add(s2);

        s1.getTeachers().add(t1);
        s1.getTeachers().add(t2);
        s2.getTeachers().add(t2);
        s2.getTeachers().add(t1);

        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(t1);
        session.save(t2);
        session.save(s1);
        session.save(s2);

        transaction.commit();
        session.close();

    }

    @Test
    public void get()
    {
        System.out.println("---------------------");
    }
}
