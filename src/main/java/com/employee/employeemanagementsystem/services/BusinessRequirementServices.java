package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.BusinessRequirement;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.BusinessRequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessRequirementServices {

    @Autowired
    private BusinessRequirementRepository businessRequirementRepository;

    public List<BusinessRequirement> findAll() throws NotFoundException {
        List<BusinessRequirement> getBusinessRequirementList = businessRequirementRepository.findAll();
        if (getBusinessRequirementList.isEmpty()) {
            throw new NotFoundException("No business requirements list found!");
        }
        return getBusinessRequirementList;
    }

    public void save(BusinessRequirement businessRequirement){
        businessRequirementRepository.save(businessRequirement);
    }
}
