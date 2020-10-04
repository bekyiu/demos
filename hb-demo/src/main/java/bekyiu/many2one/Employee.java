package bekyiu.many2one;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee
{
    private Long id;
    private String name;
    private Department dept;

    @Override
    public String toString()
    {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
