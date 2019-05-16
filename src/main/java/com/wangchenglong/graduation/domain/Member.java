package com.wangchenglong.graduation.domain;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @program: Graduation_WCL
 * @description: 集训队成员实体类
 * @author: Wangchenglong
 * @create: 2018-12-08 22:15
 **/
@Entity
public class Member implements UserDetails {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String stuId;//学号
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private  String password;
    @Column(nullable = false)
    private  String realName;//真实姓名
    private  String sex;
    private  String phone;//电话
    private  int state;//签到状态
    private  Date entryTime;//进入时间
    private  String className;//班级
    private int count;//签到次数
    private String jianjie;//简介个人信息
    @Column(length = 200)
    private String avatar; // 头像图片地址
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private List<SysRole> roles;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> auths=new ArrayList<>();
        for (GrantedAuthority sysRole : this.roles){
            auths.add(new SimpleGrantedAuthority(sysRole.getAuthority()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Member() {
        super();
    }

    public Member(String stuId, String username, String password, String realName, String sex, String phone, int state, Date entryTime, String className, int count, String jianjie, String avatar, List<SysRole> roles) {
        this.stuId = stuId;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.sex = sex;
        this.phone = phone;
        this.state = state;
        this.entryTime = entryTime;
        this.className = className;
        this.count = count;
        this.jianjie = jianjie;
        this.avatar = avatar;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", stuId='" + stuId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", state=" + state +
                ", entryTime=" + entryTime +
                ", className='" + className + '\'' +
                ", count=" + count +
                ", jianjie='" + jianjie + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles=" + roles +
                '}';
    }
}
