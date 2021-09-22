package com.employee.employeemanagementsystem.servicetest;

import com.employee.employeemanagementsystem.entities.BusinessUnit;
import com.employee.employeemanagementsystem.entities.Employee;
import com.employee.employeemanagementsystem.entities.JobProfiles;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.EmployeeRepository;
import com.employee.employeemanagementsystem.services.BusinessUnitServices;
import com.employee.employeemanagementsystem.services.EmployeeServices;
import com.employee.employeemanagementsystem.services.JobProfilesServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EmployeeServicesTest {

    @InjectMocks
    private EmployeeServices employeeServices;

    @Mock
    private EmployeeRepository employeeRepository;

    //Employee employee;

    @Test
    public void fetchEmployeeDetailsTest() throws IOException, NotFoundException {
        Employee employee = getEmployee();
        when(employeeRepository.findByEmploymentCode("HOP-2021-FTE-0001")).thenReturn(employee);
        String details = employeeServices.fetchEmployeeDetails("HOP-2021-FTE-0001");
        assertEquals(employee.toString(), details);
    }

    @Test
    public void updateJobRoleTest() throws IOException, NotFoundException {
        Employee employee = getEmployee();
        when(employeeRepository.findByEmploymentCode("HOP-2021-FTE-0001")).thenReturn(employee);
        JobProfilesServices jobProfilesServices = new JobProfilesServices();
        JobProfiles jobProfiles = new JobProfiles();
        jobProfiles.setJobRole("SDE-2");
        when(jobProfilesServices.findById("SDE-2")).thenReturn(java.util.Optional.of(jobProfiles));
        when(employeeRepository.save(employee)).thenReturn(null);
        String jobRole = employeeServices.updateJobRole("HOP-2021-FTE-0001", "SDE-2");
        assertEquals("SDE-2", jobRole);
    }

    @Test
    public void assignBusinessUnitTest() throws IOException, NotFoundException {
        Employee employee = getEmployee();
        when(employeeRepository.findByEmploymentCode("HOP-2021-FTE-0001")).thenReturn(employee);
        BusinessUnitServices businessUnitServices = new BusinessUnitServices();
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setBuName("GE");
        when(businessUnitServices.findByBuName("GE")).thenReturn(businessUnit);
        when(employeeRepository.save(employee)).thenReturn(null);
        String buName = employeeServices.assignBusinessUnit("HOP-2021-FTE-0001", "GE");
        assertEquals("GE", buName);
    }

    @Test
    public void resignNoticeTest() throws IOException, NotFoundException {
        Employee employee = getEmployee();
        when(employeeRepository.findByEmploymentCode("HOP-2021-FTE-0001")).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(null);
        String result = employeeServices.resignNotice("HOP-2021-FTE-0001");
        assertEquals(true, employee.isNoticed());
        assertEquals(LocalDate.now(), employee.getNoticeDate());
    }

    @Test
    public void createEmailIdTest() throws IOException {
        Employee employee = getEmployee();
        List<Employee> emptyList = Collections.emptyList();
        when(employeeRepository.findByEmailStartingWith("saurabh.raj")).thenReturn(emptyList);
        String email = employeeServices.createEmailId(employee);
        assertEquals("saurabh.raj@hoppipolla.com", email);
    }

    @Test
    public void buildFteIdTest() {
        String employmentCode = employeeServices.buildFteId(1);
        assertEquals("HOP-2021-FTE-0001", employmentCode);
    }

    public static Employee getEmployee() throws IOException {
        System.out.println("yoyo test");

        String file = "src/test/java/com/employee/employeemanagementsystem/constants/employeeDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        Employee employee = new Gson().fromJson(jsonObject.get("EmployeeDetails"), Employee.class);
        //List<Employee> employeeList = new ArrayList<Employee>();
        //employeeList.add(employee);
        System.out.println("User added\n");
        return employee;
        /*Employee employee = new Employee();
        employee.setFirstName("Saurabh");
        employee.setLastName("Raj");
        employee.setEmail("saurabh.raj@hoppipolla.com");
        employee.setBench(false);
        return employee;*/
    }
}
