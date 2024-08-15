package com.github.dheerajhegde.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "territories")
public class Territory {
    @Id
    @Column(name = "territory_id")
    private String territoryId;

    @Column(name = "territory_description")
    private String territoryDescription;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;

    // Default constructor
    public Territory() {
    }

    // Parameterized constructor
    public Territory(String territoryId, String territoryDescription, RegionEntity region) {
        this.territoryId = territoryId;
        this.territoryDescription = territoryDescription;
        this.region = region;
    }

    // Getters and setters
    public String getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(String territoryId) {
        this.territoryId = territoryId;
    }

    public String getTerritoryDescription() {
        return territoryDescription;
    }

    public void setTerritoryDescription(String territoryDescription) {
        this.territoryDescription = territoryDescription;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
    }
}
