package com.Product_patient.Product_patient.model;

import javax.persistence.*;

@Entity
@Table(name = "beds")
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    public Bed() {
    }

    public Bed(Integer bedId) {
        this.id = bedId;
    }

    public Integer getId(){
        return id;
    }


    @Override
    public String toString() {
        return "Bed{" +
                "id=" + id + '}';
    }

}
