package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.EmploymentType;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.services.EmploymentTypeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employment")
public class EmploymentTypeController {

    @Autowired
    private EmploymentTypeServices employmentTypeServices;

    @GetMapping("/")
    public ResponseEntity<Object> getAllEmploymentTypes(){
        try {
            List<EmploymentType> employmentTypeList = employmentTypeServices.findAll();
            return new ResponseEntity<>(employmentTypeList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public void createEmploymentType(@RequestBody EmploymentType employmentType){
        employmentTypeServices.save(employmentType);
    }
}
