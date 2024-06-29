package com.github.dheerajhegde.hibernate;

import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue; 
import javax.persistence.Id; 
import javax.persistence.JoinColumn; 
import javax.persistence.ManyToOne; 
import javax.persistence.Table;

@Entity 
@Table(name = "products") 
public class Product {

    @Id 
    @GeneratedValue 
    @Column(name = "product_id") 
    private Short productId;

    @Column(name = "product_name", nullable = false) 
    private String productName;

    @ManyToOne 
    @JoinColumn(name = "supplier_id") 
    private Supplier supplier;

    @ManyToOne 
    @JoinColumn(name = "category_id") 
    private Category category;

    @Column(name = "quantity_per_unit") 
    private String quantityPerUnit;

    @Column(name = "unit_price") 
    private Float unitPrice;

    @Column(name = "units_in_stock") 
    private Short unitsInStock;

    @Column(name = "units_on_order") 
    private Short unitsOnOrder;

    @Column(name = "reorder_level") 
    private Short reorderLevel;

    @Column(name = "discontinued", nullable = false) 
    private Boolean discontinued;

    public Product(){}
    // Constructor
    public Product(Short productId, String productName, Supplier supplier, Category category, String quantityPerUnit, Float unitPrice, Short unitsInStock, Short unitsOnOrder, Short reorderLevel, Boolean discontinued) {
        this.productId = productId;
        this.productName = productName;
        this.supplier = supplier;
        this.category = category;
        this.quantityPerUnit = quantityPerUnit;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.unitsOnOrder = unitsOnOrder;
        this.reorderLevel = reorderLevel;
        this.discontinued = discontinued;
    }

    // Getters and Setters

    public Short getProductId() {
        return productId;
    }

    public void setProductId(Short productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Short getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Short unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Short getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(Short unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public Short getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Short reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }
}