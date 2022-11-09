package com.Product_patient.Product_patient.model;


import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ProductId", nullable = false)
    private Integer productId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Product(String productName) {
        this.productName = productName;
    }

    @Column(name ="ProductName")
    private String productName;
    @Column(name = "productStatus", nullable = false)
    private String productStatus;
    @ManyToOne
    @JoinColumn(name = "PatientId")
    @NotNull
    private Patient patient;

    public Product() {
    }

    public Product(Integer productId, String productStatus, Patient patient, String productName) {
        this.productId = productId;
        this.productStatus = productStatus;
        this.patient = patient;
        this.productName = productName;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }


    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }


    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + productId +
                ", product_status='" + productStatus + '\'' +

                '}';
    }
}

