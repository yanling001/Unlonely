package com.hongyaoz.unlonelystudyservice.ServiceImp;

import com.alibaba.dubbo.config.annotation.Service;
import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import com.hongyaoz.unlonelystudyapi.pojo.Classroom;
import com.hongyaoz.unlonelystudyapi.pojo.Seat;
import com.hongyaoz.unlonelystudyapi.sercvice.SeatService;
import com.hongyaoz.unlonelystudyservice.dao.SeatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class SeatServiceImp implements SeatService {

    @Autowired
    SeatMapper seatMapper;
    public ServiceResponse chooseSeat(Seat seat) {
       // if (seat.getSeatNum()>30||seat.getSeatNum()<30)
        Seat seat1 = seatMapper.selectByseatnumandroomid(seat.getSeatNum(),seat.getRoomId());
        if (seat1==null) return ServiceResponse.createByErrorMessage("无此座位");
        if (seat1.getSeatStatus()==0){
            seat1.setSeatStatus(1);
            seatMapper.updateByPrimaryKeySelective(seat1);
            return ServiceResponse.createBysuccessMessage("选座成功");
        }
        else
        return ServiceResponse.createByErrorMessage("选座失败");
    }

    public ServiceResponse orderSeat(Seat seat) {
        Seat seat1 = seatMapper.selectByseatnumandroomid(seat.getSeatNum(), seat.getRoomId());
        if (seat1 == null) return ServiceResponse.createByErrorMessage("无此座位");
        if (seat1.getSeatStatus() == 0) {
            seat1.setSeatStatus(2);
            seatMapper.updateByPrimaryKeySelective(seat1);
            return ServiceResponse.createBysuccessMessage("预定成功");
        } else
            return ServiceResponse.createByErrorMessage("预定失败");
    }

    public ServiceResponse<List<Seat>> getfreeSeat(Classroom classroom) {
      List<Seat> list= seatMapper.selectfreeSeats(classroom.getRoomId(),0);
        return ServiceResponse.createBysuccessMessage(list);
    }

    public boolean setSeatStatus(Integer message) {
        Seat seat=new Seat();
        seat.setSeatStatus(0);
        seat.setSeatId(message);
      int count =  seatMapper.updateByPrimaryKeySelective(seat);
      if (count>0){
          return true;
      }
        return false;
    }
}
