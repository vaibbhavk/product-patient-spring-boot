package com.Product_patient.Product_patient.model;


import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @Column(name = "PatientId")
    private Integer patientId;
    @Column(name = "PatientFirstName")
    private String patientFirstName;
    @Column(name = "patientLastName")
    private String patientLastName;
    @Column(name = "patientAge")
    private Integer patientAge;
    @Column(name = "patientGender")
    private String patientGender;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bedId", referencedColumnName = "id")
    private Bed bed;

    public Patient() {
    }

    public Patient(Integer patientId, String patientFirstName, String patientLastName, Integer patientAge, String patientGender) {
        this.patientId = patientId;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }


    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", patientFirstName='" + patientFirstName + '\'' +
                ", patientLastName='" + patientLastName + '\'' +
                ", patientAge=" + patientAge +
                ", patientGender='" + patientGender + '\'' +
                '}';
    }
}