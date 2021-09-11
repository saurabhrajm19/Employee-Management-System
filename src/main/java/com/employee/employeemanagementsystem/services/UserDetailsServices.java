package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.Employee;
import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.exceptions.MyCustomException;
import com.employee.employeemanagementsystem.repository.EmployeeRepository;
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

    public List<UserDetails> findAll(){
        List<UserDetails> getUserDetailsList = userDetailsRepository.findAll();
        return getUserDetailsList;
    }

    public void save(UserDetails userDetails) throws MyCustomException {
        userDetailsRepository.save(userDetails);
        employeeServices.save(userDetails);
    }

}
