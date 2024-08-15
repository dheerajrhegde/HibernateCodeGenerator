package com.github.dheerajhegde.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "shippers")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id", nullable = false)
    private Short shipperId;

    @Column(name = "company_name", nullable = false, length = 40)
    private String companyName;

    @Column(name = "phone", length = 24)
    private String phone;

    // Default constructor
    public Shipper() {
    }

    // Parameterized constructor
    public Shipper(Short shipperId, String companyName, String phone) {
        this.shipperId = shipperId;
        this.companyName = companyName;
        this.phone = phone;
    }

    // Getters and setters
    public Short getShipperId() {
        return shipperId;
    }

    public void setShipperId(Short shipperId) {
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
