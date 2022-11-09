package com.Product_patient.Product_patient.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class StatusResponse {

    String productStatus;
    List<PatientDetail> patientList;

}
