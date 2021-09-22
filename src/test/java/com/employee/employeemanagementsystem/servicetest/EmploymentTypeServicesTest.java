package com.employee.employeemanagementsystem.servicetest;

import com.employee.employeemanagementsystem.controller.UserDetailsController;
import com.employee.employeemanagementsystem.entities.EmploymentType;
import com.employee.employeemanagementsystem.entities.JobProfiles;
import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.EmployeeRepository;
import com.employee.employeemanagementsystem.repository.EmploymentTypeRepository;
import com.employee.employeemanagementsystem.repository.JobProfilesRepository;
import com.employee.employeemanagementsystem.repository.UserDetailsRepository;
import com.employee.employeemanagementsystem.services.EmployeeServices;
import com.employee.employeemanagementsystem.services.EmploymentTypeServices;
import com.employee.employeemanagementsystem.services.JobProfilesServices;
import com.employee.employeemanagementsystem.services.UserDetailsServices;
import com.google.gson.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmploymentTypeServicesTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    EmploymentTypeRepository employmentTypeRepository;

    @Test
    public void TestUserDetails() throws NotFoundException {
        EmploymentType employmentType = new EmploymentType();
        employmentType.setEmploymentType("FTE");
        employmentType.setNoticePeriod(60);
        entityManager.persist(employmentType);
        entityManager.flush();
        System.out.println("yo yoyoyoyoyo\n");
        System.out.println(employmentTypeRepository.findAll());
    }
    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
