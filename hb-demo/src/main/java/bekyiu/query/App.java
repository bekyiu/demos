package bekyiu.query;

import bekyiu.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*
hibernate5 hql查询
https://www.cnblogs.com/miller-zou/p/5710826.html
 */
public class App
{
    @Test
    public void save()
    {
        Session session = HibernateUtil.openSession();
        session.close();
    }

    @Test
    public void jdbc()
    {
        Session session = HibernateUtil.openSession();
        session.doWork(connection -> {
            // 操作connection 无返回值
            // connection.prepareStatement()
        });

        List<Employee> list = session.doReturningWork(conn -> {
            // 带返回值
            return null;
        });
    }

    @Test
    public void q2()
    {
        Session session = HibernateUtil.openSession();
        String hql = "SELECT e FROM Employee e WHERE name LIKE ?";
        Query<Employee> query = session.createQuery(hql, Employee.class);
        query.setParameter(0, "%a%");
        List<Employee> list = query.getResultList();
        list.forEach(System.out :: println);
        System.out.println("-----------");
//        System.out.println(query.getSingleResult());
    }

    // 分页查询
    @Test
    public void page()
    {
        Session session = HibernateUtil.openSession();
        String hql = "SELECT e FROM Employee e";
        Query<Employee> query = session.createQuery(hql, Employee.class)
                .setFirstResult(0) // limit 第一个?
                .setMaxResults(5); // 第二个 ?
        List<Employee> list = query.getResultList();
        list.forEach(System.out :: println);
    }

    @Test
    public void query()
    {
        Session session = HibernateUtil.openSession();
        String hql = "SELECT e FROM Employee e WHERE name LIKE ?";
        Query query = session.createQuery(hql);
        query.setString(0, "%a%");
        List<Employee> list = query.list();
        for (Employee e : list)
        {
            System.out.println("e = " + e);
        }
        session.close();
    }

    // 1，查询所有员工【查询实体类型】
    @Test
    public void t1()
    {
        Session session = HibernateUtil.openSession();
        List<Employee> list = session.createQuery("SELECT e FROM Employee e").list();
        for (Employee e : list)
        {
            System.out.println("e = " + e + ", dept: " + e.getDept());
        }
        session.close();
    }

    // 2，查询所有员工的姓名和所属部门名称【查询特定属性】
    @Test
    public void t2()
    {
        Session session = HibernateUtil.openSession();
        List<Object[]> list = session.createQuery("SELECT e.name, e.dept FROM Employee e", Object[].class).list();
        session.close();
        for (Object[] os : list)
        {
            System.out.println("os = " + Arrays.toString(os));
        }
    }

    // Object[]处理起来不方便, 封装结果集为map
    @Test
    public void resultSetMap()
    {
        Session session = HibernateUtil.openSession();
        // 泛型是Map<String, Object> key是hql中属性的别名, 必须要用as, value是对应的值
        // 若不声明别名, 默认key是索引0, 1, 2, ...
        // 也可以 SELECT NEW LIST(...)封装为List<Object>
        List<Map> list = session
                .createQuery("SELECT NEW MAP(e.name AS name, e.dept AS dept) FROM Employee e", Map.class)
                .list();
        list.forEach(System.out :: println);
        session.close();
    }

    // 封装结果集为vo
    @Test
    public void resultSetVo()
    {
        Session session = HibernateUtil.openSession();
        // 这相当于一个构造器, 所以要vo类中要提供匹配的构造器
        // 需要配置<import class="bekyiu.query.EmployeeVO" rename="EmployeeVO"/>, 不然要写全限定名称
        List<EmployeeVO> list = session
                .createQuery("SELECT NEW EmployeeVO(e.id, e.name, e.dept) FROM Employee e", EmployeeVO.class)
                .list();
        list.forEach(System.out :: println);
        session.close();
    }


    // 3，查询出所有在成都和广州工作的员工 并且工资大于 5500 【查询结果过滤】
    @Test
    public void t3()
    {
        Session session = HibernateUtil.openSession();
        Query<Employee> query = session.createQuery("SELECT e FROM Employee e " +
                "WHERE e.dept.address.city IN (:cities) AND e.salary > :sa", Employee.class);
        // 名称占位符
        query.setParameterList("cities", new String[]{"成都", "广州"});
        query.setParameter("sa", new BigDecimal("5500"));
//        Query query = session.createQuery("SELECT e FROM Employee e " +
//                "WHERE e.dept.address.city = ? OR e.dept.address.city = ?");
//        query.setParameter(0, "成都");
//        query.setParameter(1, "广州");
        List<Employee> list = query.list();
        session.close();
        for (Employee e : list)
        {
            System.out.println("e = " + e);
        }
    }

    // 4，查询出所有员工信息，按照月薪排序【查询排序】
    @Test
    public void t4()
    {
        Session session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT e FROM Employee e ORDER BY e.salary, e.hireDate");
        List<Employee> list = query.list();
        for (Employee e : list)
        {
            System.out.println("e = " + e);
        }
        session.close();
    }

    // 5，查询出所有员工信息，按照部门编号排序【使用关联对象属性排序】
    @Test
    public void t5()
    {
        Session session = HibernateUtil.openSession();
        Query query = session.createQuery("SELECT e FROM Employee e ORDER BY e.dept.id DESC");
        List<Employee> list = query.list();
        for (Employee e : list)
        {
            System.out.println("e = " + e);
        }
        session.close();
    }
    // 6，查询出在恩宁路和八宝街上班的员工信息【使用IN】
    // 7，查询出工资在5000-6000的员工【使用BETWEEN..AND..】
    // 8，查询出姓名包含er或en的员工【使用LIKE】
    // 9，查询出有员工的部门【使用DISTINCT】
    @Test
    public void t9()
    {
        Session session = HibernateUtil.openSession();

        Query query = session.createQuery("SELECT COUNT(e.dept.id), e.dept.name FROM Employee e " +
                "GROUP BY e.dept.id, e.dept.name HAVING COUNT(e.dept.id) > 0");
        List<Object[]> list = query.list();
        for (Object[] o : list)
        {
            System.out.println("o = " + Arrays.toString(o));
        }

        session.close();
    }
}
