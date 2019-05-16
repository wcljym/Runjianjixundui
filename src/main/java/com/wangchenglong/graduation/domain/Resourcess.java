package com.wangchenglong.graduation.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: Graduation_WCL
 * @description: 资源实体类，
 * @author: Wangchenglong
 * @create: 2019-02-21 10:08
 **/
@Entity
public class Resourcess {
    @Id
    @GeneratedValue
    private Long id;
    private String software_name;//软件名称
    private String introduction;//软件简介
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE )
    private Date date;//更新时间
    private  String size;//软件大小
    private String path;//软件保存地址
    private  String  enunciation;//说明


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoftware_name() {
        return software_name;
    }

    public void setSoftware_name(String software_name) {
        this.software_name = software_name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEnunciation() {
        return enunciation;
    }

    public void setEnunciation(String enunciation) {
        this.enunciation = enunciation;
    }

    public Resourcess(String software_name, String introduction, Date date, String size, String path, String enunciation) {
        this.software_name = software_name;
        this.introduction = introduction;
        this.date = date;
        this.size = size;
        this.path = path;
        this.enunciation = enunciation;
    }

    public Resourcess() {
        super();
    }

    @Override
    public String toString() {
        return "Resourcess{" +
                "id=" + id +
                ", software_name='" + software_name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", date=" + date +
                ", size='" + size + '\'' +
                ", path='" + path + '\'' +
                ", enunciation='" + enunciation + '\'' +
                '}';
    }
}
