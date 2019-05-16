package com.wangchenglong.graduation.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: Graduation_WCL
 * @description:成绩
 * @author: Wangchenglong
 * @create: 2019-03-14 10:01
 **/

@Entity
public class Grade {
    @Id
    @GeneratedValue
    private  Long id;
    private  String realName;
    private  String stuId;//学号
    private  String className;
    private  String test_items;//测试项目
    private  double fractional_number;//分数
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE )
    private Date date;
    private  String userName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTest_items() {
        return test_items;
    }

    public void setTest_items(String test_items) {
        this.test_items = test_items;
    }

    public double getFractional_number() {
        return fractional_number;
    }

    public void setFractional_number(double fractional_number) {
        this.fractional_number = fractional_number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Grade(String realName, String stuId, String className, String test_items, double fractional_number, Date date, String userName) {
        this.realName = realName;
        this.stuId = stuId;
        this.className = className;
        this.test_items = test_items;
        this.fractional_number = fractional_number;
        this.date = date;
        this.userName = userName;
    }

    public Grade() {
        super();
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", stuId='" + stuId + '\'' +
                ", className='" + className + '\'' +
                ", test_items='" + test_items + '\'' +
                ", fractional_number=" + fractional_number +
                ", date=" + date +
                ", userName='" + userName + '\'' +
                '}';
    }
}
