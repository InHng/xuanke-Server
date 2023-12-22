package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "teacher_info")
public class TeacherInfo extends Account {
    @Column(name =  "zhicheng")
    private String zhicheng;

    public String getZhicheng() {
        return zhicheng;
    }

    public void setZhicheng(String zhicheng) {
        this.zhicheng = zhicheng;
    }
}
