package bekyiu.stream;

import org.junit.Test;

import java.util.Optional;

public class TestOptional
{
    @Test
    public void test1()
    {
        Optional<Student> op = Optional.empty();
        op.get();
    }

    @Test
    public void test2()
    {
        Optional<Object> o = Optional.of(null);
        o.get();
    }

    @Test
    public void test3()
    {
        Optional<Student> o = Optional.ofNullable(new Student());
        System.out.println(o);
        o.ifPresent(s -> s.setName("xx"));
    }

    @Test
    public void test4()
    {
        Optional<Student> o = Optional.ofNullable(new Student());
        Optional<Student> student = o.filter(s -> "xx".equals(s.getName()));
        System.out.println(student);
    }

    @Test
    public void test5()
    {
        Optional<Student> o = Optional.of(new Student(null, 100, 200.0, Status.BUSY));
        String s1 = o.map(s -> s.getName()).orElse("sb");
        System.out.println(s1);
        System.out.println(o);
    }
}
