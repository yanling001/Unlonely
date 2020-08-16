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

    public ServiceResponse opencalssroom(Integer classroom, Integer userId) {

        Classroom classroom1 = classroomMapper.selectByPrimaryKey(classroom);
        if (classroom1.getUserId()!=userId){
            return ServiceResponse.createByErrorMessage("参数错误");
        }
        classroom1.setRoomStatus(1);
        int i = classroomMapper.updateByPrimaryKeySelective(classroom1);
        if (i>0){
            return ServiceResponse.createBysuccess();
        }
        return ServiceResponse.createByErrorMessage("请重试");
    }

    public boolean checkuserAndroom(int parseInt, int parseInt1) {
        Classroom classroom1 = classroomMapper.selectByPrimaryKey(parseInt);

       if (classroom1.getUserId()==parseInt1) return true;
       else  return false;
    }

    public ServiceResponse allonlineroom() {
        return ServiceResponse.createBysuccessMessage(classroomMapper.selectallonlineroom(1));
    }

    public ServiceResponse getonlineroombybuilding(String teachingBuilding) {
        return ServiceResponse.createBysuccessMessage(classroomMapper.getonlineroombybuilding(teachingBuilding,1));
    }


}
