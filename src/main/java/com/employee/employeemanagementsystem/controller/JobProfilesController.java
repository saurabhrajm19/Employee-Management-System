package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.JobProfiles;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.services.JobProfilesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jobProfiles")
public class JobProfilesController {

    @Autowired
    private JobProfilesServices jobProfilesServices;

    @GetMapping("/")
    public ResponseEntity<Object> getAllJobProfiles(){
        try {
            List<JobProfiles> jobProfilesList = jobProfilesServices.findAll();
            return new ResponseEntity<>(jobProfilesList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public void createJobProfiles(@RequestBody JobProfiles jobProfiles){
        jobProfilesServices.save(jobProfiles);
    }
}
