package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;
    private String projectCode;
    private String startDate;
    private String endDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bu_name")
    private BusinessUnit businessUnit;

}