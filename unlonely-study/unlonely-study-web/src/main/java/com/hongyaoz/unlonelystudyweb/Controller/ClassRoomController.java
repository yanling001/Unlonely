package com.hongyaoz.unlonelystudyweb.Controller;

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
    public ServiceResponse createroom(Classroom classroom){

        if (classroom.getUserId()==null) return ServiceResponse.createByErrorMessage("参数错误");
       return roomService.createcalssroom(classroom);
    }
}
