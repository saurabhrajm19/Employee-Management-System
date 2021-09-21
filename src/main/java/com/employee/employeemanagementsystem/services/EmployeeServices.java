package com.employee.employeemanagementsystem.services;

import com.employee.employeemanagementsystem.entities.*;
import com.employee.employeemanagementsystem.exceptions.BadDetailsException;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.EmployeeRepository;
import com.employee.employeemanagementsystem.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeServices {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private EmploymentTypeServices employmentTypeServices;
    @Autowired
    private JobProfilesServices jobProfilesServices;

    public List<Employee> findAll() throws NotFoundException {
        List<Employee> getEmployeeList = employeeRepository.findAll();
        if (getEmployeeList.isEmpty()) {
            throw new NotFoundException("No employees found!");
        }
        return getEmployeeList;
    }

    public List<UserDetails> findAllUserDetails() throws NotFoundException{
        List<UserDetails> getUserDetailsList = userDetailsRepository.findAll();
        if (getUserDetailsList.isEmpty()) {
            throw new NotFoundException("No users details found!");
        }
        return getUserDetailsList;
    }

    public void save(UserDetails userDetails) throws IllegalArgumentException, BadDetailsException, NotFoundException {
        String userDetailsValidation = validateUser(userDetails, true);
        if (!userDetailsValidation.equals("valid")) {
            throw new BadDetailsException("User Details are invalid: " + userDetailsValidation);
        }
        Employee employee = new Employee();
        employee.setFirstName(userDetails.getFirstName());
        employee.setLastName(userDetails.getLastName());
        employee.setDateOfJoining(LocalDate.now());
        employee.setEmail(createEmailId(employee));
        employee.setBench(false);

        if(employmentTypeServices.findAll().isEmpty()){
            throw new IllegalArgumentException("Employment type table cannot be empty");
        }
        EmploymentType employmentType = employmentTypeServices.findById(userDetails.getEmploymentType()).orElseThrow(() -> new EntityNotFoundException("EmploymentType type not found" + userDetails.getEmploymentType()));

        if(jobProfilesServices.findAll().isEmpty()){
            throw new IllegalArgumentException("Job profile table cannot be empty");
        }
        JobProfiles jobProfiles = jobProfilesServices.findById(userDetails.getJobRole()).orElseThrow(() -> new EntityNotFoundException("Job role not found" + userDetails.getJobRole()));

        employee.setEmploymentType(employmentType);
        employee.setJobProfiles(jobProfiles);
        employee.setUserDetails(userDetails);
        employeeRepository.save(employee);
        employee.setEmploymentCode(createEmploymentCode(employee));
        employeeRepository.save(employee);
    }

    private String validateUser(UserDetails userDetails, boolean newUser) {
        if (newUser) {
            if (userDetails.getFirstName() == null) {
                return "First name cannot be null";
            }
            if (userDetails.getLastName() == null) {
                return "Last name cannot be null";
            }
            if (Objects.isNull(userDetails.getPrimaryContact())) {
                return "Primary contact field cannot be empty";
            }
            if (userDetails.getAddressDetails() == null) {
                return "Address cannot be empty";
            }
            if (userDetails.getCity() == null) {
                return "City cannot be empty";
            }
            if (Objects.isNull(userDetails.getPincode()) || userDetails.getPincode() == 0) {
                return "Pincode cannot be empty";
            }
            if (userDetails.getState() == null) {
                return "State cannot be empty";
            }
            if (Objects.isNull(userDetails.getTotalMonthsOfExperience())) {
                return "Total months of experience cannot be empty";
            }
            if (userDetails.getEmploymentType() == null) {
                return "Employment type cannot be empty";
            }
            if (userDetails.getJobRole() == null) {
                return "Job Role cannot be empty";
            }

        }

        String userPhone = String.valueOf(userDetails.getPrimaryContact());
        if (userPhone.length() != 10) {
            return "Enter a valid 10 digit Phone Number";
        }
        return "valid";
    }

    public String resignNotice(String employmentCode) throws IllegalArgumentException, NotFoundException {
        if (employeeRepository.findByEmploymentCode(employmentCode) == null){
            throw new NotFoundException("No employee with the given id found!");
        }
        Employee employee = employeeRepository.findByEmploymentCode(employmentCode);
        if (!employee.isNoticed()) {
            employee.setNoticed(true);
            LocalDate currentDate = LocalDate.now();
            employee.setNoticeDate(currentDate);
            employee = employeeRepository.save(employee);
            return "Noticed on " + currentDate.toString() +"\nYou have a notice period of "
                    + employee.getEmploymentType().getNoticePeriod();
        } else {
            throw new IllegalArgumentException("Already noticed on "+ employee.getNoticeDate());
        }
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public List<Employee> completesNoticePeriod(String date) throws NotFoundException{
        if (employeeRepository.findByNoticed(true).isEmpty()){
            throw new NotFoundException("No employee in notice period found!");
        }
        LocalDate toBeCompleted = LocalDate.parse(date, formatter);
        List<Employee> noticedEmployees = employeeRepository.findByNoticed(true);
        LocalDate fteNoticedDate = toBeCompleted.minusDays(60);
        LocalDate steNoticedDate = toBeCompleted.minusDays(15);
        LocalDate internNoticedDate = toBeCompleted.minusDays(30);
        noticedEmployees = noticedEmployees.stream().filter(x -> x.getNoticeDate().equals(fteNoticedDate) || x.getNoticeDate().equals(steNoticedDate)
                || x.getNoticeDate().equals(internNoticedDate)).collect(Collectors.toList());
        if (noticedEmployees.isEmpty()) {
            throw new NotFoundException("No employee completing their notice period on the given date");
        }
        return noticedEmployees;
    }

    public List<Employee> onboardedOnDate(String date) throws NotFoundException {
        LocalDate localDate = LocalDate.parse(date, formatter);
        if (employeeRepository.findByDateOfJoining(localDate).isEmpty()){
            throw new NotFoundException("No employee onboarded on the given date!");
        }
        return employeeRepository.findByDateOfJoining(localDate);
    }

    public String fetchEmployee(String employeeId) throws NotFoundException {
        if (employeeRepository.findByEmploymentCode(employeeId) == null) {
            throw new NotFoundException("No employee with given id found!");
        }

        String string = employeeRepository.findByEmploymentCode(employeeId).toString();
        string += employeeRepository.findByEmploymentCode(employeeId).getUserDetails().toString();
        return string;
    }

    private String createEmailId(Employee employee) {
        String domain = "@hoppipolla.com";

        String id = employee.getFirstName().toLowerCase() + "." + employee.getLastName().toLowerCase();
        List<Employee> list = employeeRepository.findByEmailStartingWith(id);
        if (!list.isEmpty()) {
            List<String> emailIdList = list.stream().map(x -> x.getEmail().split("@")[0]).collect(Collectors.toList());
            emailIdList = emailIdList.stream().sorted(String::compareTo).collect(Collectors.toList());
            String lastEmail = emailIdList.get(emailIdList.size() - 1);
            if (lastEmail.matches(".*[0-9]")) {
                int val = lastEmail.charAt(lastEmail.length() - 1) - '0';
                id += val + 1;
            } else {
                id += 1;
            }
        }
        return id+domain;
    }

    private String createEmploymentCode(Employee employee) {
        String employmentCode = "";
        if (employee.getEmploymentType().getEmploymentType().equals("STE")) {
            employmentCode = buildSteId(employee.getEmployeeId());
        } else if (employee.getEmploymentType().getEmploymentType().equals("FTE")) {
            employmentCode = buildFteId(employee.getEmployeeId());
        } else {
            employmentCode = buildInternId(employee.getEmployeeId());
        }
        return employmentCode;
    }

    private String buildSteId(int id) {
        return "HOP-STE-" + String.format("%04d", id);
    }
    private String buildInternId(int id) {
        return "HOP-INT-" + String.format("%04d", id);
    }
    private String buildFteId(int id) {
        Date date = new Date();
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        String employmentCode = "HOP-" + year.format(date) + "-FTE-" + String.format("%04d", id);
        return employmentCode;
    }

}

