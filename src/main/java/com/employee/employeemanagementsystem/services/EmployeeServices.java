package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.*;
import com.employee.employeemanagementsystem.exceptions.MyCustomException;
import com.employee.employeemanagementsystem.repository.EmployeeRepository;
import com.employee.employeemanagementsystem.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServices {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private EmploymentServices employmentServices;
    @Autowired
    private JobProfilesServices jobProfilesServices;

    public List<Employee> findAll(){
        List<Employee> getEmployeeList = employeeRepository.findAll();
        return getEmployeeList;
    }

    public List<UserDetails> findAllUserDetails(){
        List<UserDetails> getUserDetailsList = userDetailsRepository.findAll();
        return getUserDetailsList;
    }

    public void save(UserDetails userDetails) throws MyCustomException {
        try {
            Employee employee = new Employee();
            employee.setFirstName(userDetails.getFirstName());
            employee.setLastName(userDetails.getLastName());
            employee.setDateOfJoining(LocalDate.now());
            employee.setEmploymentCode(createEmploymentCode(employee));
            //employee.setEmploymentCode("123");
            employee.setEmail(createEmailId(employee));
            employee.setBench(false);
            Employment employment = employmentServices.findById(userDetails.getEmploymentType()).orElseThrow(() -> new EntityNotFoundException("Employment type not found" + userDetails.getEmploymentType()));
            employee.setEmployment(employment);
            JobProfiles jobProfiles = jobProfilesServices.findById(userDetails.getJobRole()).orElseThrow(() -> new EntityNotFoundException("Job role not found" + userDetails.getJobRole()));
            //Optional<JobProfiles> optionalJobProfiles =  jobProfilesServices.findById(userDetails.getJobRole());
            //JobProfiles jobProfiles = optionalJobProfiles.get();
            employee.setJobProfiles(jobProfiles);
            employee.setUserDetails(userDetails);
            employeeRepository.save(employee);
        } catch (Exception e) {
            throw new MyCustomException(e.getMessage());
        }
    }

    /*public void setBu(int empid, String BU){
        Employee employee = ;
        BusinessUnit businessUnit = ;
    }*/

    private String createEmailId(Employee employee) throws MyCustomException {
        String domain = "@hoppipolla.com";
        try {
            String id = employee.getFirstName().toLowerCase() + "." + employee.getLastName().toLowerCase();
            //finding and fetching if there ar other employees starting with the same id
            List<Employee> list = employeeRepository.findByEmailStartingWith(id);
            if (!list.isEmpty()) { //starting with the same id are found
                //getting only the id list removing the domain name at the end
                List<String> emailIdList = list.stream().map(x -> x.getEmail().split("@")[0]).collect(Collectors.toList());
                //sorting the ids if there are many with numbers at the end already
                emailIdList = emailIdList.stream().sorted(String::compareTo).collect(Collectors.toList());
                //getting the last id because that will be the id with highest number
                String lastEmail = emailIdList.get(emailIdList.size() - 1);
                //there could be only one email id with no trailing number, so checking it
                if (lastEmail.matches(".*[0-9]")) {
                    //if there is already a number at the end, increment the number and append it to the id
                    int val = lastEmail.charAt(lastEmail.length() - 1) - '0';
                    id += val + 1;
                } else {
                    id += 1; //if there is no number already, just append 1 to it
                }
            }
            return id+domain;
        } catch (Exception e) {
            throw new MyCustomException(e.getMessage());
        }
    }

    private String createEmploymentCode(Employee employee) throws MyCustomException {

        String employmentCode = "";
        if (employee.getEmployment().getEmploymentType() == null) {
            //throwing an exception if employee type is null because,
            // that is what based on we create the employee id
            throw new MyCustomException("Employment type cannot be null");
        }
        if (employee.getEmploymentCode() == null) {
            if (employee.getEmployment().getEmploymentType().equals("STE")) {
                employmentCode = buildSteId(employee.getEmployeeId());
            } else if (employee.getEmployment().getEmploymentType().equals("FTE")) {
                LocalDate date = LocalDate.now(); //get today's date to compare with the joining date
                SimpleDateFormat localDate = new SimpleDateFormat("yyyy-MM-dd");
                Period diff = Period.between(employee.getDateOfJoining(), date);
                System.out.println(date+"\n"+localDate+"\n"+diff);
                if (diff.toString().startsWith("P3M" + ";")) {
                    employmentCode = buildFteId(employee.getEmployeeId());
                } else {
                    employmentCode = buildInternId(employee.getEmployeeId());
                }
            } else {
                employmentCode = buildInternId(employee.getEmployeeId());
            }
        }
        return employmentCode;
    }

    String buildSteId(int id) {
        return "HOP-STE-" + String.format("%04d", id);
    }
    String buildInternId(int id) {
        return "HOP-INT-" + String.format("%04d", id);
    }
    String buildFteId(int id) {
        Date date = new Date();
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        return "HOP-" + year.format(date) + "-FTE-" + String.format("%04d", id);
    }



}