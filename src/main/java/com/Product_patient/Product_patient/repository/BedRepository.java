package com.Product_patient.Product_patient.repository;

import com.Product_patient.Product_patient.model.Bed;
import com.Product_patient.Product_patient.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedRepository extends JpaRepository<Bed, Integer> {
    @Override
    List<Bed> findAll();
}


