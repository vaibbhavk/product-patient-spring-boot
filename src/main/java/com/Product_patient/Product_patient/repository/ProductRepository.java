package com.Product_patient.Product_patient.repository;

import java.util.List;


import com.Product_patient.Product_patient.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select p.PRODUCT_ID,p.PRODUCT_STATUS,p.PATIENT_ID,p.PRODUCT_NAME from Products p where patient_id= :id", nativeQuery = true)
    public List<Product> getProductsByPatient(Integer id);
    @Query(value = "select p.PRODUCT_ID,p.PRODUCT_STATUS,p.PATIENT_ID,p.PRODUCT_NAME from Products p where PRODUCT_STATUS= :status", nativeQuery = true)
    public List<Product> getAllByStatus(String status);
}
