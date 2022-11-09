package com.Product_patient.Product_patient.controller;

import com.Product_patient.Product_patient.exception.BadRequestException;
import com.Product_patient.Product_patient.model.Patient;
import com.Product_patient.Product_patient.model.Product;
import com.Product_patient.Product_patient.serviceimpl.PatientServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientServiceImpl patientserviceImpl;


    @Test
    void testTestCreate() throws Exception {
        String URI = "/api/new";
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setPatientFirstName("Kiran");
        patient.setPatientLastName("Jadav");
        patient.setPatientAge(23);
        patient.setPatientGender("M");
        String jsonInput = this.converttoJson(patient);
        when(patientserviceImpl.addPatient(Mockito.any(Patient.class))).thenReturn(patient);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
                .content(jsonInput).contentType(MediaType.APPLICATION_JSON)).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();
        assertThat(jsonInput).isEqualTo(jsonOutput);
        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }

    @Test
    void testTestCreateWhenException() throws Exception {
        String URI = "/api/new";
        Patient patient = new Patient();
        patient.setPatientId(null);
        String jsonInput = this.converttoJson(patient);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
                .content(jsonInput).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertThatExceptionOfType(BadRequestException.class);
    }

    @Test
    void testTestUpdate() throws Exception {
        String URI = "/api/modify/{id}";
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setPatientFirstName("Kiran");
        patient.setPatientLastName("Kumar");
        patient.setPatientAge(22);
        patient.setPatientGender("M");
        String jsonInput = this.converttoJson(patient);
        when(patientserviceImpl.update(ArgumentMatchers.anyInt(), Mockito.any(Patient.class))).thenReturn(patient);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(URI, 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();
        assertThat(jsonInput).isEqualTo(jsonOutput);
        Assertions.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
    }


    @Test
    void testTestDeletePatient() throws Exception {
        String URI = "/api/delete/{id}";
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setPatientFirstName("Kiran");
        patient.setPatientLastName("Jadav");
        patient.setPatientAge(22);
        patient.setPatientGender("M");
        Product product = new Product();
        product.setProductId(1);
        product.setProductStatus("assigned");
        when(patientserviceImpl.delete(Mockito.any())).thenReturn(Arrays.asList());
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete(URI, 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String jsonOutput = mockHttpServletResponse.getContentAsString();
        assertNotNull(jsonOutput);
    }

    private String converttoJson(Object patient) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(patient);
    }

}
