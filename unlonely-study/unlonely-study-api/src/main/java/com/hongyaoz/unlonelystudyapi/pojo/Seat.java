package com.hongyaoz.unlonelystudyapi.pojo;

import java.util.Date;

public class Seat {
    private Integer seatId;

    private Integer roomId;

    private Integer seatNum;

    private Integer userId;

    private Integer seatStatus;

    private Date createTime;

    private Date updateTime;

    public Seat(Integer seatId, Integer roomId, Integer seatNum, Integer userId, Integer seatStatus, Date createTime, Date updateTime) {
        this.seatId = seatId;
        this.roomId = roomId;
        this.seatNum = seatNum;
        this.userId = userId;
        this.seatStatus = seatStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Seat() {
        super();
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(Integer seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}