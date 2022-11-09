package com.Product_patient.Product_patient.serviceimpl;

import com.Product_patient.Product_patient.exception.BadRequestException;
import com.Product_patient.Product_patient.model.Bed;
import com.Product_patient.Product_patient.model.Patient;
import com.Product_patient.Product_patient.model.Product;
import com.Product_patient.Product_patient.repository.BedRepository;
import com.Product_patient.Product_patient.repository.PatientRepository;
import com.Product_patient.Product_patient.repository.ProductRepository;
import com.Product_patient.Product_patient.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Pattern;

/*
 * Patient Service Implementation
 */
@Slf4j
@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BedRepository bedRepository;
    @Autowired
    private PatientServiceImpl patientService;


    @Override
    public Patient addPatient(Patient patient) {
        if (patient.getPatientId() != null && patient.getPatientFirstName() != null && patient.getPatientLastName() != null && patient.getPatientAge() != null && patient.getPatientGender() != null) {

            if (!assignBed(patient)) {
                throw new BadRequestException("Bed not available");
            }

            if (!patientService.patientExists(patient.getPatientId())) {
                if (patient.getPatientId() <= 0 || patient.getPatientAge() < 0 || isNumber(patient.getPatientGender()) || isNumber(patient.getPatientFirstName()) || isNumber(patient.getPatientLastName()) || patient.getPatientFirstName().isBlank() || patient.getPatientLastName().isBlank()) {
                    throw new BadRequestException("Please enter valid patient details");
                } else {
                    log.info("Patient Details saved");
                    return patientRepository.save(patient);
                }
            } else {
                log.warn("Details Existed");
                throw new BadRequestException("Patient id already exists!");
            }
        } else {
            throw new BadRequestException("Patient Details should not be null");
        }
    }

    public boolean assignBed(Patient patient) {
        if (bedRepository.findAll().size() + 1 > 500) {
            return false;
        }

        Bed bed = createBed();
        patient.setBed(bed);
        return true;

    }

    public Bed createBed() {
        Bed bed = new Bed();
        return bedRepository.save(bed);
    }

    private boolean isNumber(String patientDetail) {
        return Pattern.matches("[0-9]+", patientDetail);
    }


    @Override
    public Patient update(Integer id, Patient patient) {
        if (patient.getPatientFirstName() == null || patient.getPatientLastName() == null || patient.getPatientAge() == null || patient.getPatientGender() == null) {
            throw new BadRequestException("Please enter non null details");
        }
        if (patient.getPatientAge() < 0 || isNumber(patient.getPatientGender()) || isNumber(patient.getPatientFirstName()) || isNumber(patient.getPatientLastName())) {
            throw new BadRequestException("Please enter valid details");

        } else if (patient.getPatientId() != null) {
            throw new BadRequestException("You cannot change the Patient ID. Please remove the patient ID field from the body. ");
        } else {
            return patientRepository.findById(id).map(patients -> {
                patients.setPatientFirstName(patient.getPatientFirstName());
                patients.setPatientLastName(patient.getPatientLastName());
                patients.setPatientGender(patient.getPatientGender());
                patients.setPatientAge(patient.getPatientAge());
                log.info("Patient Details updated");
                return patientRepository.save(patients);
            }).orElseThrow(() -> new com.Product_patient.Product_patient.exception.ResourceNotFoundException("patient id:" + id + " is not found"));

        }
    }


    @Override
    public List<Product> delete(Integer id) {
        List<Product> allProductByPatient = productService.getAllProductByPatient(id);
        productRepository.getProductsByPatient(id).forEach(product -> {
            productRepository.deleteById(product.getProductId());
        });

        return patientRepository.findById(id).map(obj -> {
            patientRepository.deleteById(id);
            log.info("Patient Details deleted");
            return allProductByPatient;
        }).orElseThrow(() -> new BadRequestException("patientId: " + id + " is not found"));
    }

    @Override
    public boolean patientExists(Integer id) {
        return patientRepository.findById(id).isPresent();
    }
}
