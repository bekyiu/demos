package bekyiu.service;

import bekyiu.domain.Student;
import bekyiu.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wangyc
 * @Date: 2020/9/9 15:50
 */
@Service
public class StudentService
{
    @Autowired
    private StudentMapper studentMapper;

    /**
     * add2中的sql也会回滚
     * 因为这里的this指向的不是代理对象而是原始对象
     * 所以不存在事务的增强
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void add1()
    {
        studentMapper.insert(Student.builder().name("test1").age(26).build());
//        this.add2();

        try
        {
            int a = 1 / 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add2()
    {
        studentMapper.insert(Student.builder().name("test2").age(26).build());
//        try
//        {
//            int a = 1 / 0;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        studentMapper.insert(Student.builder().name("test3").age(26).build());
    }

    @Transactional
    public void save()
    {
        studentMapper.insert(Student.builder().name("save").age(26).build());
    }
}
