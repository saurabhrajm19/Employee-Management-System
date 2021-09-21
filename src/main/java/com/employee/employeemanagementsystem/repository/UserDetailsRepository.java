package com.employee.employeemanagementsystem.repository;

import com.employee.employeemanagementsystem.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer>{
}
