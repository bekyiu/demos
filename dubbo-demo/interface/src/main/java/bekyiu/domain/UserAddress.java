package bekyiu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserAddress implements Serializable
{
    private Long id;
    private String userAddress;
    private Long userId;
    private String phone;
}
