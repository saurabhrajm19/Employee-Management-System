package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.BusinessUnit;
import com.employee.employeemanagementsystem.entities.JobProfiles;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.BusinessUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BusinessUnitServices {

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    public List<BusinessUnit> findAll() throws NotFoundException {
        List<BusinessUnit> getBusinessUnitList = businessUnitRepository.findAll();
        if (getBusinessUnitList.isEmpty()) {
            throw new NotFoundException("No business unit found!");
        }
        return getBusinessUnitList;
    }

    public void save(BusinessUnit businessUnit){
        businessUnitRepository.save(businessUnit);
    }

    public BusinessUnit findByBuName(String buName) throws NotFoundException{
        if (Objects.isNull(businessUnitRepository.findByBuName(buName))) {
            throw new NotFoundException("No Business Unit found!");
        }
        return businessUnitRepository.findByBuName(buName);
    }

}
