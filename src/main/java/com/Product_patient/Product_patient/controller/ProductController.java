package com.Product_patient.Product_patient.controller;

import com.Product_patient.Product_patient.exception.BadRequestException;
import com.Product_patient.Product_patient.model.PatientProducts;
import com.Product_patient.Product_patient.model.Product;
import com.Product_patient.Product_patient.model.StatusResponse;
import com.Product_patient.Product_patient.serviceimpl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    /*
     * Get All products by status
     */
    @GetMapping("/products")
    public StatusResponse getAllProducts(@RequestParam("status") String status) {
        log.info("In get product method based on Status method");
        return productServiceImpl.getAllProductByStatus(status);
    }

    //Get products based on a particular id
    @GetMapping("/products/patient/{id}")
    public PatientProducts getAll(@PathVariable Integer id) {
        log.info("In get product method based on Id method");
        return productServiceImpl.getAllProductByPatientId(id);
    }


    /*
     * Create a new product under a particular category id
     */
    @PostMapping("/{id}/addProduct")
    public Product createProduct(@PathVariable Integer id, @Validated @RequestBody Product product) {
        log.info("In add product method");
        return productServiceImpl.addProduct(id, product);
    }
}
