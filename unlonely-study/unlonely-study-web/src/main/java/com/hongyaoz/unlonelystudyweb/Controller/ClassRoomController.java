package com.hongyaoz.unlonelystudyweb.Controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import com.hongyaoz.unlonelystudyapi.pojo.Classroom;
import com.hongyaoz.unlonelystudyapi.sercvice.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/room")
public class ClassRoomController {
    @Reference
    RoomService roomService;
    @RequestMapping("/createroom")
    @ResponseBody
    //创建room
    public ServiceResponse createroom(Classroom classroom){

        if (classroom.getUserId()==null) return ServiceResponse.createByErrorMessage("参数错误");
       return roomService.createcalssroom(classroom);
    }

    @RequestMapping("/allonlineroom")
    @ResponseBody
    //获取在线room
    public ServiceResponse getonlineroom(){

        return roomService.allonlineroom();
    }

    @RequestMapping("/onlineroom")
    @ResponseBody
    //获取此教学楼在线room
    public ServiceResponse getonlineroombybuilding(String teachingBuilding){

        if (StringUtils.isBlank(teachingBuilding)) return ServiceResponse.createByErrorMessage("参数错误");
        return roomService.getonlineroombybuilding(teachingBuilding);
    }
}
