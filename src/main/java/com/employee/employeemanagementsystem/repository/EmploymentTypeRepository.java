package com.employee.employeemanagementsystem.repository;

import com.employee.employeemanagementsystem.entities.EmploymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentTypeRepository extends JpaRepository<EmploymentType, String> {
}
