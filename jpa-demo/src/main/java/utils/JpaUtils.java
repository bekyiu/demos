package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @Author: wangyc
 * @Date: 2020/10/6 19:45
 */
public class JpaUtils
{
    private static EntityManagerFactory factory;

    static
    {
        factory = Persistence.createEntityManagerFactory("myJpa");
    }

    public static EntityManager getEntityManager()
    {
        return factory.createEntityManager();
    }
}
