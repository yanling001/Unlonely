package com.hongyaoz.unlonelystudyweb.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import com.hongyaoz.unlonelystudyapi.pojo.Classroom;
import com.hongyaoz.unlonelystudyapi.pojo.Seat;
import com.hongyaoz.unlonelystudyapi.sercvice.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/seat")
public class SeatController {

    @Reference
    SeatService seatService;
    @ResponseBody
    @RequestMapping("/chooseSeat")
    public ServiceResponse chooseSeat(Seat seat){
        if (seat.getRoomId()==null||seat.getUserId()==null) return ServiceResponse.createByErrorMessage("参数错误");
        return seatService.chooseSeat(seat);
    }
    @ResponseBody
    @RequestMapping("/orderSeat")
    public ServiceResponse orderSeat(Seat seat){
        if (seat.getRoomId()==null||seat.getUserId()==null) return ServiceResponse.createByErrorMessage("参数错误");
        return seatService.orderSeat(seat);
    }

    @ResponseBody
    @RequestMapping("/getfreeSeat")
    public ServiceResponse getfreeSeat(Classroom RoomId){
        if (RoomId.getRoomId()==null) return ServiceResponse.createByErrorMessage("参数错误");
        return seatService.getfreeSeat(RoomId);
    }
}
