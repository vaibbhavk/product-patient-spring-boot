package com.Product_patient.Product_patient.controller;

import com.Product_patient.Product_patient.exception.BadRequestException;
import com.Product_patient.Product_patient.model.*;
import com.Product_patient.Product_patient.repository.PatientRepository;
import com.Product_patient.Product_patient.serviceimpl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    void testFindProductById() throws Exception {
        String URI = "/api/products/patient/{id}";
        PatientProducts patientProducts = new PatientProducts();
        patientProducts.setPatientId(1);
        patientProducts.setPatientFirstName("Gautama");
        patientProducts.setPatientLastName("Bhat");
        patientProducts.setProductList(List.of(new ProductDetail()));

        String jsonInput = this.converttoJson(patientProducts);

        Mockito.when(productService.getAllProductByPatientId(1)).thenReturn(patientProducts);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(URI, patientProducts.getPatientId())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();
        System.out.println(jsonOutput);
        assertThat(jsonInput).isEqualTo(jsonOutput);
    }

    @Test
    void testFindProductByStatus() throws Exception {
        String URI = "/api/products/";
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setProductStatus("assigned");
        PatientDetail detail = new PatientDetail();
        detail.setProductId(1);
        detail.setProductName("abc");
        detail.setPatientFirstName("Gautham");
        detail.setPatientFirstName("Bhat");
        detail.setPatientId(123);
        statusResponse.setPatientList(List.of(detail));
        String jsonInput = this.converttoJson(statusResponse);
        Mockito.when(productService.getAllProductByStatus(Mockito.any())).thenReturn(statusResponse);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(URI)
                        .param("status", "assigned")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();
        assertThat(jsonInput).isEqualTo(jsonOutput);
    }

    @Test
    void testAddProduct() throws Exception {

        String URI = "/api/{id}/addProduct";
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Test");
        product.setProductStatus("assigned");
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setPatientFirstName("Kiran");
        patient.setPatientLastName("Jadav");
        patient.setPatientAge(23);
        patient.setPatientGender("M");
        product.setPatient(patient);
        String jsonInput = this.converttoJson(product);
        Mockito.when(patientRepository.save(patient)).thenReturn(patient);
        Mockito.when(productService.addProduct(patient.getPatientId(), product)).thenReturn(product);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(URI, patient.getPatientId()).accept(MediaType.APPLICATION_JSON)
                .content(jsonInput).contentType(MediaType.APPLICATION_JSON)).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }

    @Test
    void testAddProductWhenException() throws Exception {
        String URI = "/api/{id}/addProduct";
        Product product = new Product();
        product.setProductId(1);
        product.setProductStatus("assigned");
        Patient patient = new Patient();
        product.setPatient(patient);
        String jsonInput = this.converttoJson(product);
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.post(URI, patient.getPatientId()).accept(MediaType.APPLICATION_JSON)
                    .content(jsonInput).contentType(MediaType.APPLICATION_JSON)).andReturn();
        } catch (Exception e) {
            assertThatExceptionOfType(BadRequestException.class);
            Assertions.assertNotNull(e);
        }


    }


    private String converttoJson(Object product) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(product);
    }
}

