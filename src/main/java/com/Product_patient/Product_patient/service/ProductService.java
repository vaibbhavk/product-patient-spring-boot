package com.Product_patient.Product_patient.service;

import com.Product_patient.Product_patient.model.PatientProducts;
import com.Product_patient.Product_patient.model.Product;
import com.Product_patient.Product_patient.model.StatusResponse;

import java.util.List;


public interface ProductService {

    public Product addProduct(Integer id, Product product);

    public PatientProducts getAllProductByPatientId(Integer id);

    public List<Product> getAllProductByPatient(Integer id);

    public StatusResponse getAllProductByStatus(String status);
}
