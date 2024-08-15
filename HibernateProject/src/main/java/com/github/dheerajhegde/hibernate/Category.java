package com.github.dheerajhegde.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "category_id")
    private short categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @Column(name = "picture")
    private byte[] picture;

    // Default constructor
    public Category() {
    }

    // Parameterized constructor
    public Category(short categoryId, String categoryName, String description, byte[] picture) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.picture = picture;
    }

    // Getters and setters
    public short getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(short categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
