package com.hongyaoz.unlonelystudyservice.dao;

import com.hongyaoz.unlonelystudyapi.pojo.Seat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatMapper {
    int deleteByPrimaryKey(Integer seatId);

    int insert(Seat record);

    int insertSelective(Seat record);

    Seat selectByPrimaryKey(Integer seatId);

    int updateByPrimaryKeySelective(Seat record);

    int updateByPrimaryKey(Seat record);

    Seat selectByseatnumandroomid(@Param("seatNum") Integer seatNum, @Param("roomId")Integer roomId);

    List<Seat> selectfreeSeats(@Param("roomId")Integer roomId, @Param("status") Integer status);
}