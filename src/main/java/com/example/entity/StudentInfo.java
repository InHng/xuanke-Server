package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "student_info")
public class StudentInfo extends Account {
    @Column(name = "code")
    private String code;
    @Column(name = "xueyuanId")
    private Long xueyuanId;
    @Column(name = "score")
    private Integer score;
    @Transient
    public String xueyuanName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getXueyuanId() {
        return xueyuanId;
    }

    public void setXueyuanId(Long xueyuanId) {
        this.xueyuanId = xueyuanId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getXueyuanName() {
        return xueyuanName;
    }

    public void setXueyuanName(String xueyuanName) {
        this.xueyuanName = xueyuanName;
    }
}
