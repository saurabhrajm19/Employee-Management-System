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
    private int requirementCount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_role")
    private JobProfiles jobProfiles;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_code")
    private Project project;

}