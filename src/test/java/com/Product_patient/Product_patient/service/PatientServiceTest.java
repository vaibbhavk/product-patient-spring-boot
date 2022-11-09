package com.Product_patient.Product_patient.service;

import com.Product_patient.Product_patient.exception.BadRequestException;
import com.Product_patient.Product_patient.model.Patient;
import com.Product_patient.Product_patient.model.Product;
import com.Product_patient.Product_patient.repository.PatientRepository;
import com.Product_patient.Product_patient.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class PatientServiceTest {

    @MockBean
    PatientRepository patientRepository;
    @MockBean
    ProductRepository productRepository;
    @Autowired
    PatientService patientService;


    @Test
    void testAddPatient() throws Exception {
        Patient patient = new Patient();
        patient.setPatientId(7);
        patient.setPatientFirstName("Kruthika");
        patient.setPatientLastName("poojari");
        patient.setPatientAge(33);
        patient.setPatientGender("F");
        when(patientRepository.save(patient)).thenReturn(patient);
        assertThat(patientService.addPatient(patient)).isEqualTo(patient);
    }

    @Test
    void testAddUpdateWhenException() {
        Patient patient = new Patient();
        patient.setPatientId(7);
        patient.setPatientLastName("poojari");
        patient.setPatientAge(33);
        patient.setPatientGender("F");
        try {
            patientService.update(7, patient);
        } catch (BadRequestException e) {
            Assertions.assertNotNull(e);
        }
    }

    @Test
    void testAddUpdateWhenAgeException() throws Exception {
        Patient patient = new Patient();
        patient.setPatientId(7);
        patient.setPatientLastName("poojari");
        patient.setPatientAge(0);
        patient.setPatientGender("F");
        try {
            patientService.update(7, patient);
        } catch (Exception e) {
            assertThatExceptionOfType(BadRequestException.class);
            Assertions.assertNotNull(e);
        }
    }

    @Test
    void testAddUpdateWhenIdException() {
        Patient patient = new Patient();
        patient.setPatientLastName("poojari");
        patient.setPatientAge(0);
        patient.setPatientGender("F");
        try {
            patientService.update(7, patient);
        } catch (Exception e) {
            assertThatExceptionOfType(BadRequestException.class);
            Assertions.assertNotNull(e);
        }
    }

    @Test
    void testAddUpdate() {
        Patient patient = new Patient();
        patient.setPatientFirstName("abc");
        patient.setPatientLastName("poojari");
        patient.setPatientAge(0);
        patient.setPatientGender("F");
        when(patientRepository.findById(7)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);
        patientService.update(7, patient);

    }


    @Test
    void delete() {
        Patient patient = new Patient();
        Product product = new Product();
        patient.setPatientId(1);
        patient.setPatientFirstName("Kiran");
        patient.setPatientLastName("Jadav");
        patient.setPatientAge(23);
        patient.setPatientGender("M");
        when(patientRepository.findById(patient.getPatientId())).thenReturn(Optional.of(patient));
        when(patientRepository.findByPatient_id(patient.getPatientId())).thenReturn(List.of(patient));
        when(productRepository.getProductsByPatient(patient.getPatientId())).thenReturn(List.of(product));
        patientService.delete(patient.getPatientId());
        verify(patientRepository, times(1)).deleteById(patient.getPatientId());
    }

}
