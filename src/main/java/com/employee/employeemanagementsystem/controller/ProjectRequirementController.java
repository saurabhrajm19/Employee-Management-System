package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.ProjectRequirement;
import com.employee.employeemanagementsystem.services.ProjectRequirementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ProjectRequirement")
public class ProjectRequirementController {

    @Autowired
    private ProjectRequirementServices projectRequirementServices;

    @GetMapping("/")
    public List<ProjectRequirement> getAllProjectRequirements(){
        List<ProjectRequirement> projectRequirementList = projectRequirementServices.findAll();
        return projectRequirementList;
    }

    @PostMapping("/")
    public void createProjectRequirement(@RequestBody ProjectRequirement projectRequirement){
        projectRequirementServices.save(projectRequirement);
    }
}
