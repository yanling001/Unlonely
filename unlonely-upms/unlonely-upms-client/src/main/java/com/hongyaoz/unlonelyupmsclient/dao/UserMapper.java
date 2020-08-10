package com.hongyaoz.unlonelyupmsclient.dao;

import com.hongyaoz.unlonelyupmsclient.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectpasswordbyaccountnum(String accountnum);
}