package bekyiu;

import bekyiu.many2one_one2many.domain.Customer;
import bekyiu.many2one_one2many.dao.CustomerDao;
import bekyiu.many2one_one2many.domain.LinkMan;
import bekyiu.many2one_one2many.dao.LinkManDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wangyc
 * @Date: 2020/10/7 21:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Many2one
{
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    @Test
    @Transactional
    @Rollback(false)
    public void save()
    {
        Customer c = new Customer();
        c.setCustName("baidu");
        LinkMan man = new LinkMan();
        man.setLkmName("lee");
        // 双向关系
        c.getLinkMan().add(man);
        man.setCustomer(c);

        // 需要事务, 不然报错
        customerDao.save(c);
        linkManDao.save(man);
    }

    @Test
    public void find()
    {
        LinkMan man = linkManDao.findOne(4L);
        System.out.println(man.getLkmName());
        System.out.println("============================");
        System.out.println(man.getCustomer().getCustName());
    }
}
