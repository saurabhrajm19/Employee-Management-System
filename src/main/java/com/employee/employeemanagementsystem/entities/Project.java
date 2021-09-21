package com.employee.employeemanagementsystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Project {

    @Id
    private String projectCode;
    private String startDate;
    private String endDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bu_name")
    private BusinessUnit businessUnit;

    @OneToMany(mappedBy = "project")
    private List<ProjectRequirement> projectRequirementList;

}