package com.hongyaoz.unlonelystudyservice.ServiceImp;

import com.alibaba.dubbo.config.annotation.Service;
import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import com.hongyaoz.unlonelystudyapi.pojo.Classroom;
import com.hongyaoz.unlonelystudyapi.sercvice.RoomService;
import com.hongyaoz.unlonelystudyservice.dao.ClassRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service
public class RoomServiceImp  implements RoomService {

    @Autowired
    ClassRoomMapper classroomMapper;
    public ServiceResponse createcalssroom(Classroom classroom) {
       int count =classroomMapper.insert(classroom);
       if (count>0){
           return ServiceResponse.createBysuccessMessage("创建成功");
       }
       return ServiceResponse.createByErrorMessage("创建失败请重新");
    }
}
