package com.github.dheerajhegde.hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Table(name = "customer_customer_demo")
public class CustomerCustomerDemo implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Id
    @ManyToOne
    @JoinColumn(name = "customer_type_id")
    private CustomerDemographics customerDemographics;

    // Constructor
    public CustomerCustomerDemo(Customer customer, CustomerDemographics customerDemographics) {
        this.customer = customer;
        this.customerDemographics = customerDemographics;
    }

    // Getters and Setters

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerDemographics getCustomerDemographics() {
        return customerDemographics;
    }

    public void setCustomerDemographics(CustomerDemographics customerDemographics) {
        this.customerDemographics = customerDemographics;
    }
}

// Assume Customer and CustomerDemographics are Hibernate entities representing the customers and customer_demographics tables.