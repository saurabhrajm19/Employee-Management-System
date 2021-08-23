package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.Employment;
import com.employee.employeemanagementsystem.services.EmploymentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employment")
public class EmploymentController {

    @Autowired
    private EmploymentServices employmentServices;

    @GetMapping("/")
    public List<Employment> getAllEmployments(){
        List<Employment> employmentList = employmentServices.findAll();
        return employmentList;
    }

    @PostMapping("/")
    public void createEmployment(@RequestBody Employment employment){
        employmentServices.save(employment);
    }
}
