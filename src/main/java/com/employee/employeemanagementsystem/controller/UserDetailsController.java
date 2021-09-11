package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.exceptions.MyCustomException;
import com.employee.employeemanagementsystem.services.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("userDetails")
public class UserDetailsController {

    @Autowired
    private UserDetailsServices userDetailsServices;

    @GetMapping("/")
    public List<UserDetails> getAllUserDetails(){
        List<UserDetails> userDetailsList = userDetailsServices.findAll();
        return userDetailsList;
    }

    @PostMapping("/")
    public void createUserDetails(@RequestBody UserDetails userDetails) throws MyCustomException {
        userDetailsServices.save(userDetails);
    }
}
