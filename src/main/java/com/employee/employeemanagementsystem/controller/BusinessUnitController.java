package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.BusinessUnit;
import com.employee.employeemanagementsystem.services.BusinessUnitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("BusinessUnit")
public class BusinessUnitController {

    @Autowired
    private BusinessUnitServices businessUnitServices;

    @GetMapping("/")
    public List<BusinessUnit> getAllBusinessUnits(){
        List<BusinessUnit> businessUnitList = businessUnitServices.findAll();
        return businessUnitList;
    }

    @PostMapping("/")
    public void createBusinessUnit(@RequestBody BusinessUnit businessUnit){
        businessUnitServices.save(businessUnit);
    }
}
