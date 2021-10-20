package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.BusinessUnit;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.services.BusinessUnitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("BusinessUnit")
public class BusinessUnitController {

    @Autowired
    private BusinessUnitServices businessUnitServices;

    @GetMapping("/")
    public ResponseEntity<Object> getAllBusinessUnits() {
        try {
            List<BusinessUnit> businessUnitList = businessUnitServices.findAll();
            return new ResponseEntity<>(businessUnitList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public void createBusinessUnit(@RequestBody BusinessUnit businessUnit) {
        businessUnitServices.save(businessUnit);
    }
}
