package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table
@Entity
public class JobProfiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobProfileId;
    private String jobRole;
    private int experienceRequiredMin;
    private int experienceRequiredMax;
    private String department;

}