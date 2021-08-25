package com.employee.employeemanagementsystem.repository;

import com.employee.employeemanagementsystem.entities.ProjectRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRequirementRepository extends JpaRepository<ProjectRequirement, Integer> {
}
