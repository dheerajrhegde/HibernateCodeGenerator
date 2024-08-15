package com.github.dheerajhegde.hibernate;

import javax.persistence.*;
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

    // Default constructor
    public CustomerCustomerDemo() {
    }

    // Parameterized constructor
    public CustomerCustomerDemo(Customer customer, CustomerDemographics customerDemographics) {
        this.customer = customer;
        this.customerDemographics = customerDemographics;
    }

    // Getters and setters
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
