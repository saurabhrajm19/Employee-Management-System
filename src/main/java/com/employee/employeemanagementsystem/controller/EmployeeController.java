package com.employee.employeemanagementsystem.controller;

import com.amazonaws.SdkClientException;
import com.employee.employeemanagementsystem.entities.*;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.services.EmployeeServices;
import static com.employee.employeemanagementsystem.myconstants.VariableConstants.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @PutMapping(value = "/notice/{employmentCode}")
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
    public ResponseEntity<Object> getEmployeeDetails(@PathVariable String employmentCode) {
        try {
            return new ResponseEntity<>(employeeServices.fetchEmployeeDetails(employmentCode), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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


    @PutMapping(value = "/update-jobRole/")
    public String updateJobRole(@RequestBody String response){
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        String employmentCode = new Gson().fromJson(jsonObject.get(employmentCodeStr), String.class);
        String updatedJobRole = new Gson().fromJson(jsonObject.get("updatedJobRole"), String.class);
        try {
            return employeeServices.updateJobRole(employmentCode, updatedJobRole);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    @PutMapping(value = "/assign-bu/")
    public String assignBusinessUnit(@RequestBody String response){
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        String employmentCode = new Gson().fromJson(jsonObject.get(employmentCodeStr), String.class);
        String businessUnit = new Gson().fromJson(jsonObject.get(businessUnitStr), String.class);
        try {
            return employeeServices.assignBusinessUnit(employmentCode, businessUnit);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

    @PostMapping(value = "/upload-certificate/")
    public String uploadCertificate(@RequestBody String response) {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        String employmentCode = new Gson().fromJson(jsonObject.get(employmentCodeStr), String.class);
        String filePath = new Gson().fromJson(jsonObject.get("filePath"), String.class);
        try {
            return employeeServices.uploadCertificates(filePath, employmentCode);
        } catch (IOException | SdkClientException e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/download-certificate/{fileName}")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable String fileName) {
        try {
            byte[] media = employeeServices.downloadCertificate(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(media.length);
            return new ResponseEntity<>(media, headers, HttpStatus.OK);
        } catch (IOException | SdkClientException e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping(value = "/delete-certificate/{fileName}")
    public String deleteCertificate(@PathVariable String fileName) {
        try {
            return employeeServices.deleteCertificates(fileName);
        } catch (IOException | SdkClientException | NotFoundException e) {
            return e.getMessage();
        }
    }
}

