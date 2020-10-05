package bekyiu.m2o_o2m;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Department
{
    private Long id;
    private String deptName;
    private List<Employee> employees = new ArrayList<>();

    @Override
    public String toString()
    {
        return "Department{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
