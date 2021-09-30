package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.EmploymentType;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.EmploymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmploymentTypeServices {

    @Autowired
    private EmploymentTypeRepository employmentTypeRepository;

    public List<EmploymentType> findAll() throws NotFoundException {
        List<EmploymentType> getEmploymentTypeList = employmentTypeRepository.findAll();
        if (getEmploymentTypeList.isEmpty()) {
            throw new NotFoundException("No employment type found!");
        }
        return getEmploymentTypeList;
    }

    public void save(EmploymentType employmentType){
        employmentTypeRepository.save(employmentType);
    }

    public Optional<EmploymentType> findById(String employmentType) throws NotFoundException{
        if (Objects.isNull(employmentTypeRepository.findById(employmentType))) {
            throw new NotFoundException("No employment type found!");
        }
        return employmentTypeRepository.findById(employmentType);
    }

}

