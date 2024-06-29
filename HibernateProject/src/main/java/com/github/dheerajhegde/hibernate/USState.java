package com.github.dheerajhegde.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "us_states")
public class USState {

    @Id
    @Column(name = "state_id")
    private Short stateId;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_abbr")
    private String stateAbbr;

    @Column(name = "state_region")
    private String stateRegion;

    public USState(){}
    // Constructor
    public USState(Short stateId, String stateName, String stateAbbr, String stateRegion) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateAbbr = stateAbbr;
        this.stateRegion = stateRegion;
    }

    // Getters and Setters

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateAbbr() {
        return stateAbbr;
    }

    public void setStateAbbr(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public String getStateRegion() {
        return stateRegion;
    }

    public void setStateRegion(String stateRegion) {
        this.stateRegion = stateRegion;
    }
}