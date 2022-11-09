package com.Product_patient.Product_patient.service;

import com.Product_patient.Product_patient.exception.BadRequestException;
import com.Product_patient.Product_patient.model.Patient;
import com.Product_patient.Product_patient.model.PatientProducts;
import com.Product_patient.Product_patient.model.Product;
import com.Product_patient.Product_patient.model.StatusResponse;
import com.Product_patient.Product_patient.repository.PatientRepository;
import com.Product_patient.Product_patient.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ProductServiceTest {

    @MockBean
    PatientRepository patientRepository;
    @MockBean
    ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Test
    void testAddProduct() throws Exception {
        Product product1 = new Product();
        product1.setProductId(8);
        product1.setProductName("name");
        product1.setProductStatus("transfused");
        Patient patient = new Patient();
        patient.setPatientId(5);
        patient.setPatientFirstName("Harsha");
        patient.setPatientLastName("Naik");
        patient.setPatientAge(22);
        patient.setPatientGender("M");
        product1.setPatient(patient);
        product1.setPatient(patient);
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        when(productRepository.save(product1)).thenReturn(product1);
        product1 = productService.addProduct(1, product1);
        assertNotNull(product1);
    }


    @Test
    void testFindProductByID() {
        Product product = singleProduct();
        Patient patient = singlePatient();
        product.setPatient(patient);
        when(patientRepository.findByPatient_id(1)).thenReturn(List.of(patient));
        when(productRepository.getProductsByPatient(1)).thenReturn(List.of(product));
        assertNotNull(productService.getAllProductByPatient(1));
    }


    @Test
    void testGetAllProductByStatus() {
        Product product1 = new Product();
        product1.setProductId(3);
        product1.setProductStatus("crossmatched");
        Patient patient = singlePatient();
        product1.setPatient(patient);
        when(productRepository.getAllByStatus("crossmatched")).thenReturn(List.of(product1));
        StatusResponse response = productService.getAllProductByStatus("crossmatched");
        assertNotNull(response);
    }

    private Product singleProduct() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductStatus("assigned");
        product.setProductName("name");
        return product;
    }

    private Patient singlePatient() {
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setPatientFirstName("Kiran");
        patient.setPatientLastName("Jadav");
        patient.setPatientGender("M");
        patient.setPatientAge(23);
        return patient;
    }

    @Test
    void exceptionTestForAdd() {
        Product product = singleProduct();
        Patient patient = singlePatient();
        product.setPatient(patient);
        patientRepository.save(patient);
        Throwable exception = assertThrows(BadRequestException.class,
                () -> productService.addProduct(7, product));
        assertEquals("Patient Id not found", exception.getMessage());

    }

    @Test
    void exceptionTestForFindByStatus() {
        Product product = singleProduct();
        Patient patient = singlePatient();
        product.setPatient(patient);
        patientRepository.save(patient);
        Throwable exception = assertThrows(BadRequestException.class, () -> productService.getAllProductByStatus("Unknown"));
        assertEquals("No products found with status: " + "Unknown", exception.getMessage());
    }

    @Test
    void testForFindByStatusWhenStatusIsNull() {
        Product product = singleProduct();
        Patient patient = singlePatient();
        product.setPatient(patient);
        patientRepository.save(patient);
        Throwable exception = assertThrows(BadRequestException.class, () -> productService.getAllProductByStatus(null));
        assertEquals("Status cannot be null or empty", exception.getMessage());
    }


    @Test
    void exceptionTestForFindByPatient() {
        Product product = singleProduct();
        Patient patient = singlePatient();
        product.setPatient(patient);
        patientRepository.save(patient);
        Throwable exception = assertThrows(com.Product_patient.Product_patient.exception.ResourceNotFoundException.class,
                () -> productService.getAllProductByPatient(9));
        assertEquals("Patient is not present", exception.getMessage());
    }

    @Test
    void exceptionTestForFindByPatientId() {
        Product product = singleProduct();
        Patient patient = singlePatient();
        product.setPatient(patient);
        patientRepository.save(patient);
        Throwable exception = assertThrows(com.Product_patient.Product_patient.exception.ResourceNotFoundException.class,
                () -> productService.getAllProductByPatientId(9));
        assertEquals("Patient is not present", exception.getMessage());
    }

    @Test
    void testForFindByPatientId() throws Exception{
        Product product = singleProduct();
        Patient patient = singlePatient();
        product.setPatient(patient);
        when(patientRepository.findByPatient_id(Mockito.any())).thenReturn(List.of(patient));
        when(productRepository.getProductsByPatient(Mockito.any())).thenReturn(List.of(product));
        PatientProducts allProductByPatientId = productService.getAllProductByPatientId(9);
        assertNotNull(allProductByPatientId);
    }
}
