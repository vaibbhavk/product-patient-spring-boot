package com.Product_patient.Product_patient.controller;

import com.Product_patient.Product_patient.model.Patient;
import com.Product_patient.Product_patient.model.Product;
import com.Product_patient.Product_patient.serviceimpl.PatientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class PatientController {

    @Autowired
    private PatientServiceImpl patientServiceImpl;

    /*
     * Create a new patient
     */
    @PostMapping(value = "/new", consumes = "application/json")
    public Patient create(@Valid @RequestBody Patient patient) {
        log.info("In create patient method");
        return patientServiceImpl.addPatient(patient);
    }

    /*
     * update a patient based on Id
     */
    @PutMapping("/modify/{id}")
    public Patient update(@PathVariable Integer id, @RequestBody Patient patient) {
        log.info("In modify patient method");
        return patientServiceImpl.update(id, patient);
    }

    /*
     * Delete a patient based on Id
     */
    @DeleteMapping("/delete/{id}")
    public List<Product> delete(@PathVariable Integer id) {
        log.info("In delete patient method");
        return patientServiceImpl.delete(id);
    }
}
