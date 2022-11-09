package com.Product_patient.Product_patient.repository;

import com.Product_patient.Product_patient.model.Patient;
import com.Product_patient.Product_patient.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThatObject;

@RunWith(SpringRunner.class)
@Transactional
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("To Enter a new product into DB")
    void TestNewProduct() throws Exception {
        Product product = new Product();
        product.setProductId(20);
        product.setProductStatus("assigned");
        Patient patient = new Patient();
        patient.setPatientId(20);
        patient.setPatientFirstName("Kiran");
        patient.setPatientLastName("Jadav");
        patient.setPatientAge(23);
        patient.setPatientGender("M");
        product.setPatient(patient);
        Patient savePatient = testEntityManager.persist(patient);
        Product saveindb = testEntityManager.merge(product);
        Product getdb = productRepository.getProductsByPatient(savePatient.getPatientId()).get(0);
        assertThatObject(saveindb).isEqualTo(getdb);
    }

    @Test
    void TestFindById() throws Exception {
        Product product = new Product();
        product.setProductId(3);
        product.setProductStatus("crossmatched");
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setPatientFirstName("Kiran");
        patient.setPatientLastName("Jadav");
        patient.setPatientAge(23);
        patient.setPatientGender("M");
        product.setPatient(patient);
        Patient savePatient = testEntityManager.persist(patient);
        Product saveindb = testEntityManager.merge(product);
        Product getdb = productRepository.getProductsByPatient(savePatient.getPatientId()).get(0);
        assertThatObject(saveindb).isEqualTo(getdb);
    }

    @Test
    void TestFindByStatus() throws Exception {
        Product product = new Product();
        product.setProductId(1);
        product.setProductStatus("crossmatched");
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setPatientFirstName("Kiran");
        patient.setPatientLastName("Jadav");
        patient.setPatientAge(23);
        patient.setPatientGender("M");
        Patient patient2 = new Patient();
        patient2.setPatientId(1);
        patient2.setPatientFirstName("Kiran");
        patient2.setPatientLastName("Jadav");
        patient2.setPatientAge(23);
        patient2.setPatientGender("M");
        product.setPatient(patient);
        product.setPatient(patient2);
        Patient savePatient = testEntityManager.persist(patient);
        Product saveindb = testEntityManager.merge(product);
		Product getdb = productRepository.getAllByStatus(saveindb.getProductStatus()).get(0);
		assertThatObject(saveindb).isEqualTo(getdb);
    }

}
