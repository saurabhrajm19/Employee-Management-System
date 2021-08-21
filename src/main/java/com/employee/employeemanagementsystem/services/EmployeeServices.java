package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.Employee;
import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.repository.EmployeeRepository;
import com.employee.employeemanagementsystem.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServices {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public List<Employee> findAll(){
        List<Employee> getEmployeeList = employeeRepository.findAll();
        return getEmployeeList;
    }

    public List<UserDetails> findAllUserDetails(){
        List<UserDetails> getUserDetailsList = userDetailsRepository.findAll();
        return getUserDetailsList;
    }

    public void save(Employee employee){
        employeeRepository.save(employee);
    }


}