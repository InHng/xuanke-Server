package com.example.entity;

import javax.persistence.*;

@Table(name = "zhuanye_info")
public class ZhuanyeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "xueyuanId")
    private Long xueyuanId;
    @Transient
    private String xueyuanName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getXueyuanId() {
        return xueyuanId;
    }

    public void setXueyuanId(Long xueyuanId) {
        this.xueyuanId = xueyuanId;
    }

    public String getXueyuanName() {
        return xueyuanName;
    }

    public void setXueyuanName(String xueyuanName) {
        this.xueyuanName = xueyuanName;
    }
}
