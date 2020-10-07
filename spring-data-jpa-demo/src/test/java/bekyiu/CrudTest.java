package bekyiu;

import bekyiu.dao.CustomerDao;
import bekyiu.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: wangyc
 * @Date: 2020/10/7 11:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CrudTest
{
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void findOne()
    {
        // getOne()是延迟加载
        Customer c = customerDao.findOne(1L);
        System.out.println(c);
    }

    /*
    save即表示保存又表示更新
    有oid的话表示更新, 先根据oid查询再update (有null值的话会覆盖原来得值)
    没有oid表示保存
     */
    @Test
    public void save()
    {
        Customer c = new Customer();
        c.setCustId(3L);
        c.setCustAddress("3哈哈");
        customerDao.save(c);
    }

    @Test
    public void del()
    {
        customerDao.delete(3L);
    }

    @Test
    public void list()
    {
        customerDao.findAll().forEach(System.out :: println);
    }

    // 查询总记录数
    @Test
    public void count()
    {
        System.out.println(customerDao.count());
    }
}
