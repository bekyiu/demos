package bekyiu.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeVO
{
    private Long id;
    private String name;
    private Department dept;//fk dept_id;

    @Override
    public String toString()
    {
        if(dept != null)
        {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", dept=" + dept.getName() +
                    '}';
        }
        else
        {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", dept=" + "null" +
                    '}';
        }

    }
}
