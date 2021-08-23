package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.JobProfiles;
import com.employee.employeemanagementsystem.services.JobProfilesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jobProfiles")
public class JobProfilesController {

    @Autowired
    private JobProfilesServices jobProfilesServices;

    @GetMapping("/")
    public List<JobProfiles> getAllJobProfiles(){
        List<JobProfiles> jobProfilesList = jobProfilesServices.findAll();
        return jobProfilesList;
    }

    @PostMapping("/")
    public void createJobProfiles(@RequestBody JobProfiles jobProfiles){
        jobProfilesServices.save(jobProfiles);
    }
}
