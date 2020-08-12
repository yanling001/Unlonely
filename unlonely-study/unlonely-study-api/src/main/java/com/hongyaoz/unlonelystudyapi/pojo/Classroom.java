package com.hongyaoz.unlonelystudyapi.pojo;

import java.util.Date;

public class Classroom {
    private Integer roomId;

    private String roomName;

    private String roomUrl;

    private String subject;

    private String teachingBuilding;

    private Integer userId;

    private Integer roomStatus;

    private Date createTime;

    private Date updateTime;

    public Classroom(Integer roomId, String roomName, String roomUrl, String subject, String teachingBuilding, Integer userId, Integer roomStatus, Date createTime, Date updateTime) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomUrl = roomUrl;
        this.subject = subject;
        this.teachingBuilding = teachingBuilding;
        this.userId = userId;
        this.roomStatus = roomStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Classroom() {
        super();
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName == null ? null : roomName.trim();
    }

    public String getRoomUrl() {
        return roomUrl;
    }

    public void setRoomUrl(String roomUrl) {
        this.roomUrl = roomUrl == null ? null : roomUrl.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getTeachingBuilding() {
        return teachingBuilding;
    }

    public void setTeachingBuilding(String teachingBuilding) {
        this.teachingBuilding = teachingBuilding == null ? null : teachingBuilding.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
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