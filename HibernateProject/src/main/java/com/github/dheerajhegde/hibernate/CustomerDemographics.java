package com.github.dheerajhegde.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "customer_demographics")
public class CustomerDemographics {
    @Id
    @Column(name = "customer_type_id")
    private String customerTypeId;

    @Column(name = "customer_desc")
    private String customerDesc;

    @Column(name = "customertypeid")
    private String customertypeid;

    @Column(name = "customerdesc")
    private String customerdesc;

    // Default constructor
    public CustomerDemographics() {
    }

    // Parameterized constructor
    public CustomerDemographics(String customerTypeId, String customerDesc, String customertypeid, String customerdesc) {
        this.customerTypeId = customerTypeId;
        this.customerDesc = customerDesc;
        this.customertypeid = customertypeid;
        this.customerdesc = customerdesc;
    }

    // Getters and setters
    public String getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(String customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getCustomerDesc() {
        return customerDesc;
    }

    public void setCustomerDesc(String customerDesc) {
        this.customerDesc = customerDesc;
    }

    public String getCustomertypeid() {
        return customertypeid;
    }

    public void setCustomertypeid(String customertypeid) {
        this.customertypeid = customertypeid;
    }

    public String getCustomerdesc() {
        return customerdesc;
    }

    public void setCustomerdesc(String customerdesc) {
        this.customerdesc = customerdesc;
    }
}
