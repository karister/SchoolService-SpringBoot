package schoolservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import schoolservice.mybatis.entity.RepairEvent;
import schoolservice.service.RepairEventService;
import schoolservice.service.StudentService;

import java.util.List;

@SpringBootTest
class SchoolServiceApplicationTests {

    @Autowired
    private StudentService studentService;
    @Autowired
    private RepairEventService repairEventService;

    @Test
    void contextLoads() {
        System.out.println(studentService.getStuInfo("201810241116"));
        List<RepairEvent> repairEvents = repairEventService.queryEvents();
        for (RepairEvent repairEvent : repairEvents) {
            System.out.println(repairEvent.toString());
        }
    }

    @Test
    void profilePathTest(){
        studentService.modifyProfileByID("C:/dasdada","201810241116");
    }

}
