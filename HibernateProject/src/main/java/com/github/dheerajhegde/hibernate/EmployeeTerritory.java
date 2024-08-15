package com.github.dheerajhegde.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employee_territories")
public class EmployeeTerritory implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "territory_id")
    private Territory territory;

    // Default constructor
    public EmployeeTerritory() {
    }

    // Parameterized constructor
    public EmployeeTerritory(Employee employee, Territory territory) {
        this.employee = employee;
        this.territory = territory;
    }

    // Getters and setters
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }
}
