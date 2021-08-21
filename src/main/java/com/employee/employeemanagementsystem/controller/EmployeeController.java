package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.Employee;
import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeServices employeeServices;

    //@Autowired
    //private UserDetails userDetails;

    @GetMapping("/")
    public List<Employee> getAllEmployees(){
        List<Employee> employeeList = employeeServices.findAll();
        return employeeList;
    }

    @PostMapping("/")
    public void createEmployee(@RequestBody String response)throws ParseException {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();

        UserDetails userDetails = new Gson().fromJson(jsonObject.get("UserDetails"), UserDetails.class);
        Employee employee = new Gson().fromJson(response, Employee.class);
        //employee.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(jsonObject.get("date"))));

        employee.setUserDetails(userDetails);
        employeeServices.save(employee);
    }

}

