package bekyiu.many2many;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Teacher
{
    private Long id;
    private String name;
    private List<Student> students = new ArrayList<>();

    @Override
    public String toString()
    {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
