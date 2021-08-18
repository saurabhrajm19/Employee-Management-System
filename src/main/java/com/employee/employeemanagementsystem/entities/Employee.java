package com.employee.employeemanagementsystem.entities;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Table
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employment_id")
    private int employmentCode;
    private String firstName;
    private String lastName;

    @Column(name = "email_id")
    private String email;
    private boolean bench;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}