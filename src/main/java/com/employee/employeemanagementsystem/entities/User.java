package com.employee.employeemanagementsystem.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Data
@Table
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userID;
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
    private int yearOfPostGraduation;
    private String degreeObtainedInGraduation;
    private String degreeObtainedInPostGraduation;
    private String collegeDetailsGraduation;
    private String collegeDetailsPostGraduation;


}