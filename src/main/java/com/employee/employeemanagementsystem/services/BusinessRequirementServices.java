package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.BusinessRequirement;
import com.employee.employeemanagementsystem.repository.BusinessRequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessRequirementServices {

    @Autowired
    private BusinessRequirementRepository businessRequirementRepository;

    public List<BusinessRequirement> findAll(){
        List<BusinessRequirement> getBusinessRequirementList = businessRequirementRepository.findAll();
        return getBusinessRequirementList;
    }

    public void save(BusinessRequirement businessRequirement){
        businessRequirementRepository.save(businessRequirement);
    }
}
