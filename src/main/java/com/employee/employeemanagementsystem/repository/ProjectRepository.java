package com.employee.employeemanagementsystem.repository;

import com.employee.employeemanagementsystem.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
