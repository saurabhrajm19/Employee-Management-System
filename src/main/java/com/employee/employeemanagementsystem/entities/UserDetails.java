package com.employee.employeemanagementsystem.entities;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String firstName;
    private String lastName;
    private String addressDetails;
    private String city;
    private long pincode;
    private String state;
    private long primaryContact;
    private long secondaryContact;
    private int totalMonthsOfExperience;
    private String previousCompany;
    private int yearOfGraduation;
    private String degreeObtainedInGraduation;
    private String collegeDetailsGraduation;
    private int yearOfPostGraduation;
    private String degreeObtainedInPostGraduation;
    private String collegeDetailsPostGraduation;
    private String employmentType;
    private String jobRole;

    @OneToOne(mappedBy = "userDetails")
    private Employee employee;


}