package com.Product_patient.Product_patient;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductPatientApplication.class)
class ProductPatientApplicationTest {

    @Test
     void contextLoads(){
        ProductPatientApplication.main(new String[]{});

    }
}