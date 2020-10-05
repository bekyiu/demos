package bekyiu.m2o_o2m;

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
