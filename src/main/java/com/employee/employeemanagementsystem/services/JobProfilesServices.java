package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.Employment;
import com.employee.employeemanagementsystem.entities.JobProfiles;
import com.employee.employeemanagementsystem.repository.EmploymentRepository;
import com.employee.employeemanagementsystem.repository.JobProfilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobProfilesServices {

    @Autowired
    private JobProfilesRepository jobProfilesRepository;

    public List<JobProfiles> findAll(){
        List<JobProfiles> getJobProfilesList = jobProfilesRepository.findAll();
        return getJobProfilesList;
    }

    public void save(JobProfiles jobProfiles){
        jobProfilesRepository.save(jobProfiles);
    }

    public Optional<JobProfiles> findById(String jobRole){
        Optional<JobProfiles> jobProfiles = jobProfilesRepository.findById(jobRole);
        return jobProfiles;
    }
}
