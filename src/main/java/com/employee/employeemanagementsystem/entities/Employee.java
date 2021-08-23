package com.employee.employeemanagementsystem.entities;


import lombok.Data;

import javax.persistence.*;

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
    private String date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserDetails userDetails;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "employment_type")
    private Employment employment;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_role")
    private JobProfiles jobProfiles;
}