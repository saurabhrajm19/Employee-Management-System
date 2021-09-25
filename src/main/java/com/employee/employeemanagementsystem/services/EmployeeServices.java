package com.employee.employeemanagementsystem.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.employee.employeemanagementsystem.entities.*;
import com.employee.employeemanagementsystem.exceptions.BadDetailsException;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.EmployeeRepository;
import com.employee.employeemanagementsystem.repository.UserDetailsRepository;
import static com.employee.employeemanagementsystem.myconstants.JobRoles.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.nio.file.Paths;

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
    @Autowired
    private BusinessUnitServices businessUnitServices;

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
        employeeRepository.save(employee);
        employee.setEmploymentCode(createEmploymentCode(employee));
        if(userDetails.getJobRole() == null)
            userDetails.setJobRole(assignJobRole(employee.getEmploymentCode()));
        employee.setUserDetails(userDetails);
        employeeRepository.save(employee);
    }

    public String uploadCertificates(String filePath, String employmentCode) throws IOException, SdkClientException {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
        String[] cred = getAWSCred("C:\\Users\\sauraraj\\Documents\\Employee management System\\credentials.txt");
        AWSCredentials credentials = new BasicAWSCredentials(cred[0], cred[1]);
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
        List<Bucket> buckets = s3Client.listBuckets();
        PutObjectRequest request = new PutObjectRequest(buckets.get(0).getName(), employmentCode, new File(filePath));
        ObjectMetadata metadata = new ObjectMetadata();
        //metadata.setContentType("plain/text");
        metadata.addUserMetadata("FileInfo", "File for employee with employment code :" + employmentCode);
        request.setMetadata(metadata);
        s3Client.putObject(request);
        return "File Upload Successful.";
    }

    public String fetchEmployeeDetails(String employmentCode) throws NotFoundException {
        if (employeeRepository.findByEmploymentCode(employmentCode) == null) {
            throw new NotFoundException("No employee with given id found!");
        }
        return employeeRepository.findByEmploymentCode(employmentCode).toString();
    }

    public String updateJobRole(String employmentCode, String updatedJobRole) throws NotFoundException {
        if (employeeRepository.findByEmploymentCode(employmentCode) == null) {
            throw new NotFoundException("No employee with given id found!");
        }
        Employee employee = employeeRepository.findByEmploymentCode(employmentCode);
        JobProfiles jobProfiles = jobProfilesServices.findById(updatedJobRole).orElseThrow(() -> new EntityNotFoundException("Job role not found" + updatedJobRole));
        employee.setJobProfiles(jobProfiles);
        employeeRepository.save(employee);
        return employee.getJobProfiles().getJobRole();
    }

    public String assignBusinessUnit(String employmentCode, String buName) throws NotFoundException {
        if (employeeRepository.findByEmploymentCode(employmentCode) == null) {
            throw new NotFoundException("No employee with given id found!");
        }
        Employee employee = employeeRepository.findByEmploymentCode(employmentCode);
        BusinessUnit businessUnit = businessUnitServices.findByBuName(buName);
        employee.setBusinessUnit(businessUnit);
        employeeRepository.save(employee);
        return employee.getBusinessUnit().getBuName();
    }

    public String assignJobRole(String employmentCode) throws NotFoundException {
        if (employeeRepository.findByEmploymentCode(employmentCode) == null) {
            throw new NotFoundException("No employee with given id found!");
        }
        Employee employee = employeeRepository.findByEmploymentCode(employmentCode);
        int exp = employee.getUserDetails().getTotalMonthsOfExperience()/12;
        String degree = "";
        if (employee.getUserDetails().getDegreeTypeInPostGraduation() != null)
            degree =  employee.getUserDetails().getDegreeTypeInPostGraduation();
        else
            degree = employee.getUserDetails().getDegreeTypeInGraduation();

        switch (degree.toUpperCase()) {
            case DEGREES.TECHNICAL:
                return assignTechnicalRole(exp);
            case DEGREES.MANAGEMENT:
                return assignManagementRole(exp);
            case DEGREES.RECRUITMENT:
                return assignRecruitmentRole(exp);
            case DEGREES.FINANCE:
                return assignFinanceRole(exp);
            case DEGREES.HR:
                return assignHrRole(exp);
            default:
                throw new NotFoundException("Degree not found");
        }
    }

    public void deleteByEmploymentCode(String employmentCode) throws NotFoundException {
        if (employeeRepository.findByEmploymentCode(employmentCode) == null) {
            throw new NotFoundException("No employee with given id found!");
        }
        employeeRepository.deleteByEmploymentCode(employmentCode);
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
            employeeRepository.save(employee);
            return "Noticed on " + currentDate.toString();
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

    private String[] getAWSCred(String filePath) throws IOException {
        String reader = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] arrOfStr = reader.split(",");
        return arrOfStr;
    }

    public String validateUser(UserDetails userDetails, boolean newUser) {
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
        }

        String userPhone = String.valueOf(userDetails.getPrimaryContact());
        if (userPhone.length() != 10) {
            return "Enter a valid 10 digit Phone Number";
        }
        return "valid";
    }

    public String createEmailId(Employee employee) {
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
    public String createEmploymentCode(Employee employee) {
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
    public String buildSteId(int id) {
        return "HOP-STE-" + String.format("%04d", id);
    }
    public String buildInternId(int id) {
        return "HOP-INT-" + String.format("%04d", id);
    }
    public String buildFteId(int id) {
        Date date = new Date();
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        String employmentCode = "HOP-" + year.format(date) + "-FTE-" + String.format("%04d", id);
        return employmentCode;
    }

    private String assignHrRole(int exp) {
        if (exp>=0 && exp<8) {
            return HR_ROLES.HR_EXEC;
        }else {
            return HR_ROLES.HR_HEAD;
        }
    }
    private String assignFinanceRole(int exp) {
        if (exp>=0 && exp<8) {
            return FINANCE_ROLES.FIN_EXEC;
        }else {
            return FINANCE_ROLES.FIN_HEAD;
        }
    }
    private String assignRecruitmentRole(int exp) {
        if (exp>=0 && exp<8) {
            return RECRUITMENT_ROLES.REC_SPECIALS;
        }else {
            return RECRUITMENT_ROLES.REC_HEAD;
        }
    }
    private String assignManagementRole(int exp) {
        if (exp>=0 && exp<5) {
            return MANAGEMENT_ROLES.APM;
        } else if (exp>=5 && exp<10) {
            return MANAGEMENT_ROLES.PM;
        } else {
            return MANAGEMENT_ROLES.SPM;
        }
    }
    private String assignTechnicalRole(int exp) {
        if (exp>=0 && exp<2) {
            return TECHNICAL_ROLES.SDE_1;
        } else if (exp>=2 && exp<5) {
            return TECHNICAL_ROLES.SDE_2;
        } else if (exp>=5 && exp<8) {
            return TECHNICAL_ROLES.SDE_3;
        } else if (exp>=8 && exp<10) {
            return TECHNICAL_ROLES.LEAD;
        } else {
            return TECHNICAL_ROLES.EM;
        }
    }

}

