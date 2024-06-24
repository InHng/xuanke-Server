package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "admin_info")
public class AdminInfo extends Account {
    @Column(name =  "phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
