package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class ProjectRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectRequirementId;
    private String jobRole;
    private String projectCode;
    private int requirementCount;

}