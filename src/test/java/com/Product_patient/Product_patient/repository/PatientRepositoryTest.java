package com.Product_patient.Product_patient.repository;

import com.Product_patient.Product_patient.model.Patient;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatObject;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("To Enter a new patient into DB")
    public void testNewPatient() throws Exception {
        Patient obj = new Patient();
        obj.setPatientId(6);
        obj.setPatientFirstName("Kruthika");
        obj.setPatientLastName("Venki");
        obj.setPatientAge(20);
        obj.setPatientGender("F");
        Patient saveindb = testEntityManager.persist(obj);
        Patient getdb = patientRepository.findByPatient_id(saveindb.getPatientId()).get(0);
        assertThatObject(saveindb).isEqualTo(getdb);
    }


    @Test
    @DisplayName("To update a new patient into DB")
    public void testupdate() throws Exception {
        Patient patient1 = new Patient();
        patient1.setPatientId(4);
        testEntityManager.persist(patient1);
        patient1.setPatientFirstName("Thushitha");
        testEntityManager.persist(patient1);
        patient1.setPatientLastName("SRS");
        testEntityManager.persist(patient1);
        patient1.setPatientAge(22);
        testEntityManager.persist(patient1);
        patient1.setPatientGender("F");
        testEntityManager.persist(patient1);
        assertEquals("SRS", patient1.getPatientLastName());
    }

}
