package com.employee.employeemanagementsystem.repository;

import com.employee.employeemanagementsystem.entities.BusinessRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRequirementRepository extends JpaRepository<BusinessRequirement, Integer> {
}
