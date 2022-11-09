package com.Product_patient.Product_patient.serviceimpl;

import com.Product_patient.Product_patient.exception.BadRequestException;
import com.Product_patient.Product_patient.exception.ResourceNotFoundException;
import com.Product_patient.Product_patient.model.*;
import com.Product_patient.Product_patient.repository.PatientRepository;
import com.Product_patient.Product_patient.repository.ProductRepository;
import com.Product_patient.Product_patient.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/*
 * Product Service Implementation
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PatientRepository patientRepository;

    @Override
    public Product addProduct(Integer id, Product product) {
        if (product.getProductName() == null || product.getProductName().isEmpty() || isNumber(product.getProductName())) {
            throw new BadRequestException("Product Name cannot be null or empty");
        }
        if (product.getProductStatus() == null || product.getProductStatus().isEmpty() || isNumber(product.getProductStatus())) {
            throw new BadRequestException("Product Status cannot be null or empty");
        }
        if (Objects.equals(product.getProductStatus().toLowerCase(Locale.ROOT), "dispensed")
                || Objects.equals(product.getProductStatus().toLowerCase(Locale.ROOT), "assigned")
                || Objects.equals(product.getProductStatus().toLowerCase(Locale.ROOT), "crossmatched")
                || Objects.equals(product.getProductStatus().toLowerCase(Locale.ROOT), "transfused")) {
            return patientRepository.findById(id).map(prod -> {
                product.setPatient(prod);
                log.info("Product Details saved");
                return productRepository.save(product);
            }).orElseThrow(() -> new BadRequestException("Patient Id not found"));
        } else {
            throw new BadRequestException("Please enter valid product status");
        }
    }

    @Override
    public PatientProducts getAllProductByPatientId(@Pattern(regexp = "[0-9]+", message = " Patient ID can always be an integer") Integer id) {
        List<Patient> patients = patientRepository.findByPatient_id(id);
        if (!patients.isEmpty()) {
            List<Product> plist = productRepository.getProductsByPatient(id);
            PatientProducts patientProducts = new PatientProducts();
            if (!plist.isEmpty()) {
                List<ProductDetail> productsList = new ArrayList<>();
                plist.forEach(product -> {
                    transform(product, patientProducts, productsList);
                });
                return patientProducts;
            } else
                throw new ResourceNotFoundException("Product is not present");
        } else {
            throw new ResourceNotFoundException("Patient is not present");
        }
    }

    @Override
    public List<Product> getAllProductByPatient(@Pattern(regexp = "[0-9]+", message = " Patient ID can always be an integer") Integer id) {
        List<Patient> patients = patientRepository.findByPatient_id(id);
        if (!patients.isEmpty()) {
            List<Product> plist = productRepository.getProductsByPatient(id);
            if (!plist.isEmpty()) {
                return plist;
            } else
                throw new ResourceNotFoundException("Product is not present");
        } else {
            throw new ResourceNotFoundException("Patient is not present");
        }
    }

    private boolean isNumber(String productDetail) {
        return java.util.regex.Pattern.matches("[0-9]+", productDetail);
    }


    private PatientProducts transform(Product product, PatientProducts patientProducts, List<ProductDetail> productsList) {
        patientProducts.setPatientId(product.getPatient().getPatientId());
        patientProducts.setPatientFirstName(product.getPatient().getPatientFirstName());
        patientProducts.setPatientLastName(product.getPatient().getPatientLastName());
        ProductDetail product1 = new ProductDetail();
        product1.setProductId(product.getProductId());
        product1.setProductName(product.getProductName());
        product1.setStatus(product.getProductStatus());
        productsList.add(product1);
        patientProducts.setProductList(productsList);
        return patientProducts;
    }

    @Override
    public StatusResponse getAllProductByStatus(String status) {
        if (status == null || status.isEmpty()) {
            log.warn("Invalid details");
            throw new BadRequestException("Status cannot be null or empty");
        }
        List<Product> allByStatus = productRepository.getAllByStatus(status);
        if (allByStatus.isEmpty()) {
            log.warn("Products Not found with patient");
            throw new BadRequestException("No products found with status: " + status);
        } else {
            StatusResponse statusResponse = new StatusResponse();
            List<PatientDetail> patientDetails = new ArrayList<>();
            log.info("All Products associated with patient");
            allByStatus.forEach(product -> {
                createStatusResponse(statusResponse, product, patientDetails);
            });
            return statusResponse;
        }
    }

    private StatusResponse createStatusResponse(StatusResponse statusResponse, Product product, List<PatientDetail> patientDetails) {
        statusResponse.setProductStatus(product.getProductStatus());
        PatientDetail detail = new PatientDetail();
        detail.setProductId(product.getProductId());
        detail.setProductName(product.getProductName());
        detail.setPatientId(product.getPatient().getPatientId());
        detail.setPatientFirstName(product.getPatient().getPatientFirstName());
        detail.setPatientLastName(product.getPatient().getPatientLastName());
        patientDetails.add(detail);
        statusResponse.setPatientList(patientDetails);
        return statusResponse;
    }
}
