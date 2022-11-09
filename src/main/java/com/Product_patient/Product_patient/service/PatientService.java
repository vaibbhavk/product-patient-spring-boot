package com.Product_patient.Product_patient.service;

import com.Product_patient.Product_patient.model.Bed;
import com.Product_patient.Product_patient.model.Patient;
import com.Product_patient.Product_patient.model.Product;

import java.util.List;

public interface
PatientService {

    public Patient addPatient(Patient patient);

    public Patient update(Integer id, Patient patient);

    public List<Product> delete(Integer id);

    public boolean patientExists(Integer id);


}
