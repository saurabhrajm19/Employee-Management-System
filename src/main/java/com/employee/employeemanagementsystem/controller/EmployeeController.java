package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.*;
import com.employee.employeemanagementsystem.exceptions.BadDetailsException;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.services.EmployeeServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeServices employeeServices;

    @GetMapping("/")
    public ResponseEntity<Object> getAllEmployees(){
        try {
            List<Employee> employeeList = employeeServices.findAll();
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/notice/{employmentCode}")
    public ResponseEntity<String> resignNotice(@PathVariable String employmentCode) {
        try {
            return new ResponseEntity<>(employeeServices.resignNotice(employmentCode), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/{employmentCode}")
    public String getEmployeeDetails(@PathVariable String employmentCode) {
        try {
            return employeeServices.fetchEmployeeDetails(employmentCode);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/onboard/{date}")
    public ResponseEntity<Object> onboardedOnDate(@PathVariable String date) {
        try {
            return new ResponseEntity<>(employeeServices.onboardedOnDate(date), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/completes-notice/{date}")
    public ResponseEntity<Object> completesNoticePeriod(@PathVariable String date) {
        try {
            return new ResponseEntity<>(employeeServices.completesNoticePeriod(date), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update-jobRole/{employmentCode}")
    public String updateJobRole(@RequestBody String response){
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        String employmentCode = new Gson().fromJson(jsonObject.get("employmentCode"), String.class);
        String updatedJobRole = new Gson().fromJson(jsonObject.get("updatedJobRole"), String.class);
        try {
            return employeeServices.updateJobRole(employmentCode, updatedJobRole);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    @PutMapping(value = "/assign-bu/{employmentCode}")
    public String assignBusinessUnit(@RequestBody String response){
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        String employmentCode = new Gson().fromJson(jsonObject.get("employmentCode"), String.class);
        String businessUnit = new Gson().fromJson(jsonObject.get("businessUnit"), String.class);
        try {
            return employeeServices.assignBusinessUnit(employmentCode, businessUnit);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/upload-certificate/")
    public String uploadCertificates() throws Exception {
        return employeeServices.uploadCertificates();
    }

}

