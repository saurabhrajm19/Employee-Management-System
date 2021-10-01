package com.employee.employeemanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class EmploymentType {

    @Id
    private String employmentType;
    private int noticePeriod;

    @OneToMany(mappedBy = "employmentType")
    @JsonIgnore
    private List<Employee> employeeList;

}