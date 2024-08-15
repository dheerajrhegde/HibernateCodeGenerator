package com.github.dheerajhegde.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "region")
public class RegionEntity {
    @Id
    @Column(name = "region_id")
    private Short regionId;

    @Column(name = "region_description")
    private String regionDescription;

    @Column(name = "regiondescription")
    private String regiondescription;

    // Default constructor
    public RegionEntity() {
    }

    // Parameterized constructor
    public RegionEntity(Short regionId, String regionDescription, String regiondescription) {
        this.regionId = regionId;
        this.regionDescription = regionDescription;
        this.regiondescription = regiondescription;
    }

    // Getters and setters
    public Short getRegionId() {
        return regionId;
    }

    public void setRegionId(Short regionId) {
        this.regionId = regionId;
    }

    public String getRegionDescription() {
        return regionDescription;
    }

    public void setRegionDescription(String regionDescription) {
        this.regionDescription = regionDescription;
    }

    public String getRegiondescription() {
        return regiondescription;
    }

    public void setRegiondescription(String regiondescription) {
        this.regiondescription = regiondescription;
    }
}
