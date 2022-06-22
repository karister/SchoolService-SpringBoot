package schoolservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import schoolservice.mybatis.entity.RepairEvent;
import schoolservice.service.RepairEventService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author karister
 * @create 2021-07-31 14:38
 */

@Controller
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private RepairEventService repairEventService;

    @RequestMapping("/form")
    @ResponseBody
    String getForm(HttpServletRequest request){
        String stuid = request.getParameter("stuid");
        String category = request.getParameter("category");
        String detail = request.getParameter("detail");
        String place = request.getParameter("place");
        RepairEvent repairEvent = new RepairEvent();
        repairEvent.setSid(stuid);
        repairEvent.setCategory(category);
        repairEvent.setDetail(detail);
        repairEvent.setPlace(place);
        System.out.println(repairEvent);
        repairEventService.addRepairEvent(repairEvent);
        repairEventService.setUpdateTime(new Timestamp(new Date().getTime()),repairEvent.getId());
        return "";
    }
}
