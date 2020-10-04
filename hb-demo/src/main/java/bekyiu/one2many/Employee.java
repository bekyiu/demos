package bekyiu.one2many;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee
{
    private Long id;
    private String name;

    @Override
    public String toString()
    {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
