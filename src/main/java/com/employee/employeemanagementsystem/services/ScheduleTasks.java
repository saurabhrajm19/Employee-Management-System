package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.Employee;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ScheduleTasks {

    @Autowired
    EmployeeServices employeeServices;

    @Scheduled(cron = "0 0 18 ? * * *")
    public void disableEmployee() throws NotFoundException {
        List<Employee> employeeList = employeeServices.completesNoticePeriod(LocalDate.now().toString());
        if (!employeeList.isEmpty()){
            int iterator = 0;
            String employmentCode = "";
            while(iterator != employeeList.size()) {
                employmentCode = employeeList.get(iterator).getEmploymentCode();
                employeeServices.deleteByEmploymentCode(employmentCode);
                iterator+=1;
            }
        }
    }

    @Scheduled(cron = "0 0 18 ? * * *")
    public void updateJobRole() throws NotFoundException {
        List<Employee> employeeList = employeeServices.findAll();
        LocalDate currentDate = LocalDate.now();
        if (!employeeList.isEmpty()){
            int iterator = 0;
            int totalMonthsOfExperience = 0;
            Period period = null;
            String jobRole = "";
            Employee employee = new Employee();
            while(iterator != employeeList.size()) {
                employee = employeeList.get(iterator);
                LocalDate dateOfJoining = employee.getDateOfJoining();
                period = Period.between(dateOfJoining, currentDate);
                totalMonthsOfExperience = Math.abs(period.getMonths()) + employee.getUserDetails().getTotalMonthsOfExperience();
                employee.getUserDetails().setTotalMonthsOfExperience(totalMonthsOfExperience);
                jobRole = employeeServices.assignJobRole(employee.getEmploymentCode());
                if(jobRole != employee.getUserDetails().getJobRole())
                    employeeServices.updateJobRole(employee.getEmploymentCode(), jobRole);
                iterator+=1;
            }
        }
    }
}
