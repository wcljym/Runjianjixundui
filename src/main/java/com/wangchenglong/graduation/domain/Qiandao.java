package com.wangchenglong.graduation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @program: Graduation_WCL
 * @description: 签到
 * @author: Wangchenglong
 * @create: 2019-02-27 23:40
 **/
@Entity
public class Qiandao {
    @Id
    @GeneratedValue
    private  int id;
    private  String username;
    private  String stuId;
    private  String realName;
    private Date  date;// 签到的时
    private  String ip;//签到的ip地址
    private int  state;// 签到的状态，0-为签到 1签到
    private  int count;//签到次数

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Qiandao(String username, String stuId, String realName, Date date, String ip, int state, int count) {
        this.username = username;
        this.stuId = stuId;
        this.realName = realName;
        this.date = date;
        this.ip = ip;
        this.state = state;
        this.count = count;
    }

    public Qiandao() {
        super();
    }

    @Override
    public String toString() {
        return "Qiandao{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", stuId='" + stuId + '\'' +
                ", realName='" + realName + '\'' +
                ", date=" + date +
                ", ip='" + ip + '\'' +
                ", state=" + state +
                ", count=" + count +
                '}';
    }
}
