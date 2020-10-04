package bekyiu.one2many;

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
    private List<Employee> list = new ArrayList<>();

    @Override
    public String toString()
    {
        return "Department{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
