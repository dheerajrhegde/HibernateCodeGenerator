package com.github.dheerajhegde.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "us_states")
public class UsState {
    @Id
    @Column(name = "state_id")
    private short stateId;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_abbr")
    private String stateAbbr;

    @Column(name = "state_region")
    private String stateRegion;

    @Column(name = "stateabbr")
    private String stateabbr;

    @Column(name = "statename")
    private String statename;

    @Column(name = "stateregion")
    private String stateregion;

    // Default constructor
    public UsState() {
    }

    // Parameterized constructor
    public UsState(short stateId, String stateName, String stateAbbr, String stateRegion, String stateabbr, String statename, String stateregion) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateAbbr = stateAbbr;
        this.stateRegion = stateRegion;
        this.stateabbr = stateabbr;
        this.statename = statename;
        this.stateregion = stateregion;
    }

    // Getters and setters
    public short getStateId() {
        return stateId;
    }

    public void setStateId(short stateId) {
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

    public String getStateabbr() {
        return stateabbr;
    }

    public void setStateabbr(String stateabbr) {
        this.stateabbr = stateabbr;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getStateregion() {
        return stateregion;
    }

    public void setStateregion(String stateregion) {
        this.stateregion = stateregion;
    }
}
