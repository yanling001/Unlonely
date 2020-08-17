package com.hongyaoz.unlonelytimetask.pojo;

import java.util.Date;

public class Task {
    private Integer taskId;

    private String email;

    private Integer status;

    private Date createTime;

    private Date excuteTime;

    public Task(Integer taskId, String email, Integer status, Date createTime, Date excuteTime) {
        this.taskId = taskId;
        this.email = email;
        this.status = status;
        this.createTime = createTime;
        this.excuteTime = excuteTime;
    }

    public Task() {
        super();
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(Date excuteTime) {
        this.excuteTime = excuteTime;
    }
}