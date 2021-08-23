package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.Employment;
import com.employee.employeemanagementsystem.repository.EmploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmploymentServices {

    @Autowired
    private EmploymentRepository employmentRepository;

    public List<Employment> findAll(){
        List<Employment> getEmploymentList = employmentRepository.findAll();
        return getEmploymentList;
    }

    public void save(Employment employment){
        employmentRepository.save(employment);
    }

}
