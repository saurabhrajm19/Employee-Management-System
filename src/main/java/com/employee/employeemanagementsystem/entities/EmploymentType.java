package com.employee.employeemanagementsystem.entities;

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
    private List<Employee> employeeList;

}