package bekyiu.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Phone
{
    private Long id;
    private PhoneType types;
    private String number;
    private Employee employee;//fk employee_id
}
