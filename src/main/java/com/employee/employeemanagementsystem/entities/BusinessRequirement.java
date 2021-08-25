package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class BusinessRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int businessRequirementId;
    private String jobRole;
    private String buName;
    private int requirementCount;

}