package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.BusinessRequirement;
import com.employee.employeemanagementsystem.services.BusinessRequirementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("BusinessRequirement")
public class BusinessRequirementController {

    @Autowired
    private BusinessRequirementServices businessRequirementServices;

    @GetMapping("/")
    public List<BusinessRequirement> getAllBusinessRequirement(){
        List<BusinessRequirement> businessRequirementList = businessRequirementServices.findAll();
        return businessRequirementList;
    }

    @PostMapping("/")
    public void createBusinessRequirement(@RequestBody BusinessRequirement businessRequirement){
        businessRequirementServices.save(businessRequirement);
    }
}
