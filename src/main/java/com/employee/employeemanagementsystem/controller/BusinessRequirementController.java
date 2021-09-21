package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.BusinessRequirement;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.services.BusinessRequirementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("BusinessRequirement")
public class BusinessRequirementController {

    @Autowired
    private BusinessRequirementServices businessRequirementServices;

    @GetMapping("/")
    public ResponseEntity<Object> getAllBusinessRequirement(){
        try {
            List<BusinessRequirement> businessRequirementList = businessRequirementServices.findAll();
            return new ResponseEntity<>(businessRequirementList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public void createBusinessRequirement(@RequestBody BusinessRequirement businessRequirement){
        businessRequirementServices.save(businessRequirement);
    }
}
