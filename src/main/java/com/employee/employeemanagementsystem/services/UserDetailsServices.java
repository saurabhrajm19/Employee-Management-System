package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.exceptions.BadDetailsException;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServices {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private EmployeeServices employeeServices;

    public List<UserDetails> findAll() throws NotFoundException {
        List<UserDetails> getUserDetailsList = userDetailsRepository.findAll();
        if (getUserDetailsList.isEmpty()) {
            throw new NotFoundException("No user details found!");
        }
        return getUserDetailsList;
    }

    public void save(UserDetails userDetails) throws BadDetailsException, NotFoundException {
        userDetailsRepository.save(userDetails);
        employeeServices.save(userDetails);
    }

}
