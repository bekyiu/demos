package bekyiu.mapper;

import bekyiu.domain.Student;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: wangyc
 * @Date: 2020/9/8 20:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentMapperTest
{
    @Autowired
    private StudentMapper mapper;

    @Test
    public void selectById()
    {
        System.out.println(mapper.selectById(1L));
    }

    @Test
    public void select()
    {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        // mp的like会自己动%, 定制的话要用apply
        wrapper.ge("age", 18).apply("name like {0}", "n%e");
        mapper.selectList(wrapper).forEach(System.out :: println);
    }

    @Test
    public void page()
    {
        // 分页查询需要配置插件PaginationInterceptor才能生效
        Page<Student> pages = mapper.selectPage(new Page<>(1, 2), null);
        pages.getRecords().forEach(System.out :: println);
    }

    @Test
    public void insert()
    {
        mapper.insert(Student.builder().name("nishino").age(26).build());
    }

}