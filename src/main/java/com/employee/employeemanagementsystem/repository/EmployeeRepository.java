package com.employee.employeemanagementsystem.repository;

import com.employee.employeemanagementsystem.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

    List<Employee> findByEmailStartingWith(String id);
    Employee findByEmploymentCode(String employeeId);
    List<Employee> findByNoticed(boolean noticed);
    List<Employee> findByDateOfJoiningBetween(LocalDate date1, LocalDate date2);
    List<Employee> findByDateOfJoining(LocalDate date);
    int deleteByEmploymentCode(String employmentCode);
}