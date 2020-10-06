package bekyiu.query;

import bekyiu.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/10/6 11:38
 */
public class JoinTest
{
    // 查询出 所有 员工和部门名称 join
    @Test
    public void q1()
    {
        Session session = HibernateUtil.openSession();
//        String hql = "SELECT e.id, e.name, d.name FROM Employee e LEFT JOIN Department d ON e.dept.id = d.id";
        String hql = "SELECT e.id, e.name, d.name FROM Employee e LEFT JOIN e.dept d";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> list = query.getResultList();
        list.forEach(os -> {
            System.out.println(Arrays.toString(os));
        });
    }

    // 查询出各个部门员工的平均工资和最高工资
    // sql: select max(salary), avg(salary) from employee group by dept_id
    @Test
    public void q2()
    {
        Session session = HibernateUtil.openSession();
        String hql = "SELECT MAX(e.salary), AVG(e.salary) FROM Employee e GROUP BY e.dept.id";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> list = query.getResultList();
        list.forEach(os -> {
            System.out.println(Arrays.toString(os));
        });
    }

    // 查询大于平均工资的员工信息
    // sql: select * from employee where salary >
    // (select avg(salary) from employee)
    @Test
    public void q3()
    {
        Session session = HibernateUtil.openSession();
        String hql = "SELECT e FROM Employee e WHERE e.salary > " +
                "(SELECT AVG(e.salary) FROM Employee e)";
        Query<Employee> query = session.createQuery(hql, Employee.class);
        List<Employee> list = query.getResultList();
        list.forEach(System.out :: println);
    }

    // 命名查询, 不会每次都翻译hql
    // 形式上有点像mybatis, hql写在xml里
    @Test
    public void q4()
    {
        Session session = HibernateUtil.openSession();
        Query<Employee> query = session
                .createNamedQuery("findById", Employee.class)
                .setParameter(0, 1L);
        List<Employee> list = query.getResultList();
        list.forEach(System.out :: println);
    }

    // 命名sql查询
    @Test
    public void q5()
    {
        Session session = HibernateUtil.openSession();
        // sql query过时, 替换为native query
        Query query = session.getNamedNativeQuery("findByName")
                .setParameter(0, "%ar%");
        List<Object[]> list = query.getResultList();
        list.forEach(os -> {
            System.out.println(Arrays.toString(os));
        });
    }

    // sql查询
    @Test
    public void q6()
    {
        Session session = HibernateUtil.openSession();
        Query<Employee> query = session
                .createNativeQuery("SELECT * FROM employee WHERE salay > ?", Employee.class)
                .setParameter(1, new BigDecimal("5000")); // 从1开始
        query.getResultList().forEach(e -> {
            System.out.println(e + " =========== " + (e.getDept() == null ? "null" : e.getDept().getName()));
        });
    }

}
