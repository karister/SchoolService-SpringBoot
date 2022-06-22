package schoolservice.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schoolservice.mybatis.mapper.RepairEventMapper;
import schoolservice.mybatis.entity.RepairEvent;
import schoolservice.service.RepairEventService;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author karister
 * @create 2021-07-31 11:13
 */
@Service
public class RepairEventServiceImpl implements RepairEventService {

    private RepairEventMapper repairEventMapper;

    @Autowired
    public void setRepairEventMapper(RepairEventMapper repairEventMapper) {
        this.repairEventMapper = repairEventMapper;
    }

    @Override
    public void addRepairEvent(RepairEvent repairEvent) {
        repairEventMapper.addRepairEvent(repairEvent);
    }

    @Override
    public List<RepairEvent> queryEvents() {
        return repairEventMapper.queryEvents();
    }

    @Override
    public RepairEvent queryEventByID(int id) {
        return repairEventMapper.queryEventByID(id);
    }

    public List<RepairEvent> queryEventsBySid(int sid) {
        return repairEventMapper.queryEventsBySid(sid);
    }

    @Override
    public void setUpdateTime(Timestamp updateTime,int eventID) {
        repairEventMapper.setUpdateTime(updateTime,eventID);
    }
}
