package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.*;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.services.EmployeeServices;
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
    public String getEmployee(@PathVariable String employmentCode) {
        try {
            return employeeServices.fetchEmployee(employmentCode);
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

    /*
    @PostMapping("/")
    public void createEmployee(@RequestBody String response)throws ParseException {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();

        UserDetails userDetails = new Gson().fromJson(jsonObject.get("UserDetails"), UserDetails.class);
        EmploymentType employment = new Gson().fromJson(jsonObject.get("EmploymentType"), EmploymentType.class);
        JobProfiles jobProfiles = new Gson().fromJson(jsonObject.get("JobProfiles"), JobProfiles.class);
        BusinessUnit businessUnit = new Gson().fromJson(jsonObject.get("BusinessUnit"), BusinessUnit.class);
        //List<Project> projectList = new ArrayList<>();
        //projectList = new Gson().fromJson(jsonObject.get("Project"), Project.class);
        Employee employee = new Gson().fromJson(response, Employee.class);
        //employee.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(jsonObject.get("date"))));

        employee.setUserDetails(userDetails);
        employee.setEmploymentType(employment);
        employee.setJobProfiles(jobProfiles);
        employee.setBusinessUnit(businessUnit);
        //employee.setProjectList(project);
        //employeeServices.save(employee);
    }*/

}

