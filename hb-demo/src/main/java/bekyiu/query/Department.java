package bekyiu.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department
{
    private Long id;
    private String name;
    private Address address;
    private Employee manager; //fk manager_id
    private String sn;
}
