package com.github.dheerajhegde.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shippers")
public class Shipper {

    @Id
    @Column(name = "shipper_id")
    private short shipperId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "phone")
    private String phone;

    public Shipper(){}
    // Constructor
    public Shipper(short shipperId, String companyName, String phone) {
        this.shipperId = shipperId;
        this.companyName = companyName;
        this.phone = phone;
    }

    // Getters and Setters

    public short getShipperId() {
        return shipperId;
    }

    public void setShipperId(short shipperId) {
        this.shipperId = shipperId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}