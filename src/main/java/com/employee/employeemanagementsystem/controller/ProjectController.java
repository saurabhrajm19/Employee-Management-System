package com.employee.employeemanagementsystem.controller;

import com.employee.employeemanagementsystem.entities.*;
import com.employee.employeemanagementsystem.services.ProjectServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("Project")
public class ProjectController {

    @Autowired
    private ProjectServices projectServices;

    @GetMapping("/")
    public List<Project> getAllProjects(){
        List<Project> projectList = projectServices.findAll();
        return projectList;
    }

    @PostMapping("/")
    public void createProject(@RequestBody String response)throws ParseException {

        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();

        BusinessUnit businessUnit = new Gson().fromJson(jsonObject.get("BusinessUnit"), BusinessUnit.class);
        Project project = new Gson().fromJson(response, Project.class);
        project.setBusinessUnit(businessUnit);
        projectServices.save(project);
    }

}
