package bekyiu.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class User
{
    private Long id;
    private String name;
    private String headImg;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;
}
