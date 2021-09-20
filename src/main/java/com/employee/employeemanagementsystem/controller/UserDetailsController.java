package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.exceptions.BadDetailsException;
import com.employee.employeemanagementsystem.exceptions.MyCustomException;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.services.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("userDetails")
public class UserDetailsController {

    @Autowired
    private UserDetailsServices userDetailsServices;

    @GetMapping("/")
    public ResponseEntity<Object> getAllUserDetails(){
        try {
            List<UserDetails> userDetailsList = userDetailsServices.findAll();
            return new ResponseEntity<>(userDetailsList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public String createUserDetails(@RequestBody UserDetails userDetails) {
        try {
            userDetailsServices.save(userDetails);
            String response = "Done";
            return response;
        } catch (NotFoundException | BadDetailsException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/onBoardSTE")
    public String onBoardSTE(@RequestBody UserDetails userDetails) {
        try {
            userDetails.setEmploymentType("STE");
            userDetailsServices.save(userDetails);
            String response = "Done";
            return response;
        } catch (NotFoundException | BadDetailsException e) {
            return e.getMessage();
        }
    }


}
