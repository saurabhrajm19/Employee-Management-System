package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.ProjectRequirement;
import com.employee.employeemanagementsystem.repository.ProjectRequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectRequirementServices {

    @Autowired
    private ProjectRequirementRepository projectRequirementRepository;

    public List<ProjectRequirement> findAll(){
        List<ProjectRequirement> getProjectRequirementList = projectRequirementRepository.findAll();
        return getProjectRequirementList;
    }

    public void save(ProjectRequirement projectRequirement){
        projectRequirementRepository.save(projectRequirement);
    }
}
