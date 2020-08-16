package com.hongyaoz.unlonelystudyapi.sercvice;

import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import com.hongyaoz.unlonelystudyapi.pojo.Classroom;
import com.hongyaoz.unlonelystudyapi.pojo.Seat;

import java.util.List;

public interface SeatService {
    ServiceResponse chooseSeat(Seat seat);
    ServiceResponse orderSeat(Seat seat);
    ServiceResponse<List<Seat>> getfreeSeat(Classroom classroom);

    boolean setSeatStatus(Integer message);
}
