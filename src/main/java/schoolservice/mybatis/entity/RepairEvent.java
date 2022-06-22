package schoolservice.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author karister
 * @create 2021-07-31 10:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairEvent {
    private int id;
    private String category;
    private int state;
    private String title;
    private String detail;
    private String updateTime;
    private String createTime;
    private String sid;
    private String place;
}
