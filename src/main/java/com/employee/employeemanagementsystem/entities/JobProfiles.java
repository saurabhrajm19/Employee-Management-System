package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table
@Entity
public class JobProfiles {

    @Id
    private String jobRole;
    private int experienceRequiredMin;
    private int experienceRequiredMax;
    private String department;

    @OneToMany(mappedBy = "jobProfiles")
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "jobProfiles")
    private List<BusinessRequirement> businessRequirementList;

    @OneToMany(mappedBy = "jobProfiles")
    private List<ProjectRequirement> projectRequirementList;

}