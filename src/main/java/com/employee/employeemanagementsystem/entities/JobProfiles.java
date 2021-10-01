package com.employee.employeemanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class JobProfiles {

    @Id
    private String jobRole;
    private int experienceRequiredMin;
    private int experienceRequiredMax;
    private String department;

    @OneToMany(mappedBy = "jobProfiles")
    @JsonIgnore
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "jobProfiles")
    @JsonIgnore
    private List<BusinessRequirement> businessRequirementList;

    @OneToMany(mappedBy = "jobProfiles")
    @JsonIgnore
    private List<ProjectRequirement> projectRequirementList;

}