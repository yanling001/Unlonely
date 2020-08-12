package com.hongyaoz.unlonelystudyservice.dao;

import com.hongyaoz.unlonelystudyapi.pojo.Classroom;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomMapper {
    int deleteByPrimaryKey(Integer roomId);

    int insert(Classroom record);

    int insertSelective(Classroom record);

    Classroom selectByPrimaryKey(Integer roomId);

    int updateByPrimaryKeySelective(Classroom record);

    int updateByPrimaryKey(Classroom record);
}