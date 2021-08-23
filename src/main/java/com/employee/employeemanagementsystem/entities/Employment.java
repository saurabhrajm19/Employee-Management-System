package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table
@Entity
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employmentId;
    private String employmentType;
    private int noticePeriod;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "employment")
    //private List<Employee> employeeList;

}