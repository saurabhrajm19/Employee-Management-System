package com.employee.employeemanagementsystem.repository;

import com.employee.employeemanagementsystem.entities.JobProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobProfilesRepository extends JpaRepository<JobProfiles, Integer> {
}
