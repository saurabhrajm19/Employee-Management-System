package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class BusinessRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int businessRequirementId;
    private int requirementCount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bu_name")
    private BusinessUnit businessUnit;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_role")
    private JobProfiles jobProfiles;

}