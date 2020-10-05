package bekyiu.query;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Project
{
    private Long id;
    private String name;
    private Employee manager;//fk manager_id
    //many2many
    private List<Employee> employees = new ArrayList<>();
}
