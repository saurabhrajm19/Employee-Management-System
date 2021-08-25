package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class BusinessUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int businessUnitId;
    private String buName;
    private String directorDelivery;
    private String directorTechnology;

}