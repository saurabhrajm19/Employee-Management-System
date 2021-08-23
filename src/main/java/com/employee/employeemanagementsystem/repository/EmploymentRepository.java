package com.employee.employeemanagementsystem.repository;

import com.employee.employeemanagementsystem.entities.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Integer> {
}
