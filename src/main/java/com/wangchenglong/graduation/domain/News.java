package com.wangchenglong.graduation.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: Graduation_WCL
 * @description: 最新资讯
 * @author: Wangchenglong
 * @create: 2019-02-21 10:09
 **/
@Entity
public class News {
    @Id
    @GeneratedValue
    private int id;//主键
    private String title;//标题
    private String author;//
    private String content;
    private Date date;
    private String jiesao;
    private  String style;
//
//    @OneToOne(cascade=CascadeType.ALL)
//    @JoinTable(name = "news_type", joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"))
//    private Type type;//资讯的类型
    private  String  image;//新闻资讯缩略图
    private  int count;//阅读数

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJiesao() {
        return jiesao;
    }

    public void setJiesao(String jiesao) {
        this.jiesao = jiesao;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public News() {
        super();
    }

    public News(String title, String author, String content, Date date, String jiesao, String style, String image, int count) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.jiesao = jiesao;
        this.style = style;
        this.image = image;
        this.count = count;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", jiesao='" + jiesao + '\'' +
                ", style='" + style + '\'' +
                ", image='" + image + '\'' +
                ", count=" + count +
                '}';
    }
}
