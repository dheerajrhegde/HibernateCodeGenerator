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
    private String customerTypeIdHibernate;

    @Column(name = "customerdesc")
    private String customerDescHibernate;

    public CustomerDemographics(){}
    // Constructor
    public CustomerDemographics(String customerTypeId, String customerDesc, String customerTypeIdHibernate, String customerDescHibernate) {
        this.customerTypeId = customerTypeId;
        this.customerDesc = customerDesc;
        this.customerTypeIdHibernate = customerTypeIdHibernate;
        this.customerDescHibernate = customerDescHibernate;
    }

    // Getters and Setters

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

    public String getCustomerTypeIdHibernate() {
        return customerTypeIdHibernate;
    }

    public void setCustomerTypeIdHibernate(String customerTypeIdHibernate) {
        this.customerTypeIdHibernate = customerTypeIdHibernate;
    }

    public String getCustomerDescHibernate() {
        return customerDescHibernate;
    }

    public void setCustomerDescHibernate(String customerDescHibernate) {
        this.customerDescHibernate = customerDescHibernate;
    }
}