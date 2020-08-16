package com.hongyaoz.unlonelystudyapi.sercvice;

import com.hongyaoz.unlonelycommon.common.ServiceResponse;
import com.hongyaoz.unlonelystudyapi.pojo.Classroom;

public interface RoomService {

    ServiceResponse createcalssroom(Classroom classroom);

    ServiceResponse opencalssroom(Integer classroom,Integer userId);

    boolean checkuserAndroom(int parseInt, int parseInt1);

    ServiceResponse allonlineroom();

    ServiceResponse getonlineroombybuilding(String teachingBuilding);
}
