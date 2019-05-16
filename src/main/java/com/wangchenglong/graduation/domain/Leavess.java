package com.wangchenglong.graduation.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @program: Graduation_WCL
 * @description: 请假
 * @author: Wangchenglong
 * @create: 2019-03-15 15:40
 **/

@Entity
public class Leavess {
    @Id
    @GeneratedValue
    private  Long id;
    private String  userName;
    private String image;//上传手写请假条的图片地址
    private  String realName;
    private  String className;
    private  String stuId;
    private Date star_date;
    private  int state;//状态1-请假成功 0请假未验证
    private  String remarks;//备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public Date getStar_date() {
        return star_date;
    }

    public void setStar_date(Date star_date) {
        this.star_date = star_date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Leavess(String userName, String image, String realName, String className, String stuId, Date star_date, int state, String remarks) {
        this.userName = userName;
        this.image = image;
        this.realName = realName;
        this.className = className;
        this.stuId = stuId;
        this.star_date = star_date;
        this.state = state;
        this.remarks = remarks;
    }

    public Leavess() {
        super();
    }

    @Override
    public String toString() {
        return "Leavess{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", image='" + image + '\'' +
                ", realName='" + realName + '\'' +
                ", className='" + className + '\'' +
                ", stuId='" + stuId + '\'' +
                ", star_date=" + star_date +
                ", state=" + state +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
