package bekyiu.query;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Employee
{
    private Long id;
    private String name;
    private Department dept;//fk dept_id;
    private BigDecimal salary;
    private Date hireDate;//date

    @Override
    public String toString()
    {
        if(dept != null)
        {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", dept=" + dept.getId() +
                    ", salary=" + salary +
                    ", hireDate=" + hireDate +
                    '}';
        }
        else
        {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", dept=" + "null" +
                    ", salary=" + salary +
                    ", hireDate=" + hireDate +
                    '}';
        }

    }
}
