package com.wangchenglong.graduation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @program: Graduation_WCL
 * @description: 新闻资讯的类型
 * @author: Wangchenglong
 * @create: 2019-03-04 16:36
 **/
@Entity
public class Type  {
        @Id
        @GeneratedValue
        private  int id;
        private  String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type() {
        super();
    }

    public Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
