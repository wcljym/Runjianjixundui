package com.wangchenglong.graduation.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: Graduation_WCL
 * @description: 公告
 * @author: Wangchenglong
 * @create: 2019-03-09 17:10
 **/
@Entity
public class Announcement {


    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String publisher;//发布人
    private  String introduction;//简介
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE )
    private Date date;
    private String content;//内容
    private int day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Announcement(String title, String publisher, String introduction, Date date, String content, int day) {
        this.title = title;
        this.publisher = publisher;
        this.introduction = introduction;
        this.date = date;
        this.content = content;
        this.day = day;
    }

    public Announcement() {
        super();
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", introduction='" + introduction + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", day=" + day +
                '}';
    }
}
