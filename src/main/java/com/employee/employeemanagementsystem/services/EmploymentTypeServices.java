package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.EmploymentType;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.EmploymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmploymentTypeServices {

    @Autowired
    private EmploymentRepository employmentRepository;

    public List<EmploymentType> findAll() throws NotFoundException {
        List<EmploymentType> getEmploymentTypeList = employmentRepository.findAll();
        if (getEmploymentTypeList.isEmpty()) {
            throw new NotFoundException("No employment type found!");
        }
        return getEmploymentTypeList;
    }

    public void save(EmploymentType employmentType){
        employmentRepository.save(employmentType);
    }

    public Optional<EmploymentType> findById(String employmentType) throws NotFoundException{
        if (Objects.isNull(employmentRepository.findById(employmentType))) {
            throw new NotFoundException("No employment type found!");
        }
        Optional<EmploymentType> employment = employmentRepository.findById(employmentType);
        return employment;
    }



}
