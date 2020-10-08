package bekyiu;

import bekyiu.many2many.dao.RoleDao;
import bekyiu.many2many.dao.UserDao;
import bekyiu.many2many.domain.Role;
import bekyiu.many2many.domain.User;
import bekyiu.many2one_one2many.dao.CustomerDao;
import bekyiu.many2one_one2many.dao.LinkManDao;
import bekyiu.many2one_one2many.domain.Customer;
import bekyiu.many2one_one2many.domain.LinkMan;
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
public class Many2Many
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Test
    @Transactional
    @Rollback(false)
    public void save()
    {
        User u = new User();
        u.setUsername("nanase");
        u.setAge(26);
        User u1 = new User();
        u1.setUsername("bekyiu");
        u1.setAge(22);


        Role r = new Role();
        r.setRoleName("admin");

        // 关系
        u.getRoles().add(r);
        u1.getRoles().add(r);

        r.getUsers().add(u);
        r.getUsers().add(u1);

        userDao.save(u);
        userDao.save(u1);
        roleDao.save(r);
    }

}
