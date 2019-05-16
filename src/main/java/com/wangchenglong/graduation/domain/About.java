package com.wangchenglong.graduation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @program: Graduation_WCL
 * @description: 关于我们的实体类
 * @author: Wangchenglong
 * @create: 2019-03-01 11:53
 **/

@Entity
public class About {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String copy;
    @Column(length = 200)
    private  String img;//图片地址
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public About() {
        super();
    }



    public About(String title, String copy, String img, Date date) {
        this.title = title;
        this.copy = copy;
        this.img = img;
        this.date = date;
    }

    @Override
    public String toString() {
        return "About{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", copy='" + copy + '\'' +
                ", img='" + img + '\'' +
                ", date=" + date +
                '}';
    }
}
