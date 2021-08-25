package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.Project;
import com.employee.employeemanagementsystem.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServices {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> findAll(){
        List<Project> getProjectList = projectRepository.findAll();
        return getProjectList;
    }

    public void save(Project project){
        projectRepository.save(project);
    }

}
