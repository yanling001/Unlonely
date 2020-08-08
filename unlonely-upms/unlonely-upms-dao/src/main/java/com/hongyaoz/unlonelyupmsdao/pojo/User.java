package com.hongyaoz.unlonelyupmsdao.pojo;

import com.hongyaoz.unlonelyupmsdao.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class User {
    private Integer userId;

    private String nickname;

    private String accountnum;

    private String password;

    private String city;

    private String province;

    private String country;

    private String headurl;

    private Integer age;

    private Integer sex;

    private String professional;

    private String email;

    private String phone;

    private Date createTime;

    private Date updateTime;

    public User(Integer userId, String nickname, String accountnum, String password, String city, String province, String country, String headurl, Integer age, Integer sex, String professional, String email, String phone, Date createTime, Date updateTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.accountnum = accountnum;
        this.password = password;
        this.city = city;
        this.province = province;
        this.country = country;
        this.headurl = headurl;
        this.age = age;
        this.sex = sex;
        this.professional = professional;
        this.email = email;
        this.phone = phone;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getAccountnum() {
        return accountnum;
    }

    public void setAccountnum(String accountnum) {
        this.accountnum = accountnum == null ? null : accountnum.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl == null ? null : headurl.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional == null ? null : professional.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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