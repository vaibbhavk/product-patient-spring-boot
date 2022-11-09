package com.Product_patient.Product_patient.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PatientDetail {
    Integer productId;
    String productName;
    private Integer patientId;
    private String patientFirstName;
    private String patientLastName;
}
