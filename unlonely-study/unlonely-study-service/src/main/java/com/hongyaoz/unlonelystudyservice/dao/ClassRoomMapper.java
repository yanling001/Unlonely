package com.hongyaoz.unlonelystudyservice.dao;

import com.hongyaoz.unlonelystudyapi.pojo.Classroom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClassRoomMapper {
    int deleteByPrimaryKey(Integer roomId);

    int insert(Classroom record);

    int insertSelective(Classroom record);

    Classroom selectByPrimaryKey(Integer roomId);

    int updateByPrimaryKeySelective(Classroom record);

    int updateByPrimaryKey(Classroom record);

    List<Classroom> selectallonlineroom(int roomStatus);

    List<Classroom> getonlineroombybuilding(@Param("teachingBuilding") String teachingBuilding, @Param("roomStatus") Integer roomStatus);
}