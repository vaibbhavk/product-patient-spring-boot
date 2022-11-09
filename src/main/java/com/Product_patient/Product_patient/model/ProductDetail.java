package com.Product_patient.Product_patient.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductDetail {
    Integer productId;
    String productName;
    String status;
}