package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.JobProfiles;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.JobProfilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobProfilesServices {

    @Autowired
    private JobProfilesRepository jobProfilesRepository;

    public List<JobProfiles> findAll() throws NotFoundException {
        List<JobProfiles> getJobProfilesList = jobProfilesRepository.findAll();
        if (getJobProfilesList.isEmpty()) {
            throw new NotFoundException("No job profiles found!");
        }
        return getJobProfilesList;
    }

    public void save(JobProfiles jobProfiles){
        jobProfilesRepository.save(jobProfiles);
    }

    public Optional<JobProfiles> findById(String jobRole) throws NotFoundException{
        if (Objects.isNull(jobProfilesRepository.findById(jobRole))) {
            throw new NotFoundException("No job profiles found!");
        }
        Optional<JobProfiles> jobProfiles = jobProfilesRepository.findById(jobRole);
        return jobProfiles;
    }
}

