package com.employee.employeemanagementsystem.repository;

import com.employee.employeemanagementsystem.entities.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Integer> {
    BusinessUnit findByBuName(String buName);
}
