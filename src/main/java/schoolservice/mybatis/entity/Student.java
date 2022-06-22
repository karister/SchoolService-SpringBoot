package schoolservice.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author karister
 * @create 2021-07-29 3:13
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private String id;
    private String faculty;
    private String stuClass;
    private String userName;
    private String userPass;
    private String phone;
    private String profile;
}
