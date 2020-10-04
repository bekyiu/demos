package bekyiu.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
    private HibernateUtil(){}
    private static SessionFactory sf = null;
    static
    {
        sf = new Configuration().configure().buildSessionFactory();
    }

    public static Session openSession()
    {
        return sf.openSession();
    }

    public static Session getCurrentSession()
    {
        return sf.getCurrentSession();
    }

    public static void close()
    {
        sf.close();
    }
}
