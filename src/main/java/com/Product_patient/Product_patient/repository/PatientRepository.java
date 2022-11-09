package com.Product_patient.Product_patient.repository;

import java.util.List;

import com.Product_patient.Product_patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "Select * from patient p where patient_id= :id", nativeQuery = true)
    public List<Patient> findByPatient_id(Integer id);
}
