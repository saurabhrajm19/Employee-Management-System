package com.employee.employeemanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class BusinessUnit {

    @Id
    private String buName;
    private String directorDelivery;
    private String directorTechnology;

    @OneToMany(mappedBy = "businessUnit")
    private List<Project> projectList;

    @OneToMany(mappedBy = "businessUnit")
    @JsonIgnore
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "businessUnit")
    private List<BusinessRequirement> businessRequirementList;

}