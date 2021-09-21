package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.ProjectRequirement;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.services.ProjectRequirementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ProjectRequirement")
public class ProjectRequirementController {

    @Autowired
    private ProjectRequirementServices projectRequirementServices;

    @GetMapping("/")
    public ResponseEntity<Object> getAllProjectRequirements(){
        try {
            List<ProjectRequirement> projectRequirementList = projectRequirementServices.findAll();
            return new ResponseEntity<>(projectRequirementList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public void createProjectRequirement(@RequestBody ProjectRequirement projectRequirement){
        projectRequirementServices.save(projectRequirement);
    }
}
