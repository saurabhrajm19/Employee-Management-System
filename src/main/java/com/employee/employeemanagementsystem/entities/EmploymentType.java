package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table
@Entity
public class EmploymentType {

    @Id
    //private int empid;
    private String employmentType;
    private int noticePeriod;

    @OneToMany(mappedBy = "employmentType")
    private List<Employee> employeeList;

}