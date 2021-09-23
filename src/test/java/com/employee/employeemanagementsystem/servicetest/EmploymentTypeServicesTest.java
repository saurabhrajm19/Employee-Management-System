package com.employee.employeemanagementsystem.servicetest;

import com.employee.employeemanagementsystem.entities.*;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.EmploymentTypeRepository;
import com.employee.employeemanagementsystem.services.EmploymentTypeServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EmploymentTypeServicesTest {

    @InjectMocks
    private EmploymentTypeServices employmentTypeServices;

    @Mock
    private EmploymentTypeRepository employmentTypeRepository;

    @Test
    public void findAllTest() throws IOException, NotFoundException {
        when(employmentTypeRepository.findAll()).thenReturn(getEmploymentType());
        List<EmploymentType> employmentTypeList = employmentTypeServices.findAll();
        assertEquals(2, employmentTypeList.size());
        assertEquals("FTE", employmentTypeList.get(0).getEmploymentType());
        assertEquals("STE", employmentTypeList.get(1).getEmploymentType());
        assertEquals(60, employmentTypeList.get(0).getNoticePeriod());
        assertEquals(15, employmentTypeList.get(1).getNoticePeriod());
    }

    @Test
    public void findByIdTest() throws IOException, NotFoundException {
        EmploymentType employmentType = getEmploymentType().get(0);
        when(employmentTypeRepository.findById("FTE")).thenReturn(java.util.Optional.ofNullable(employmentType));
        Optional<EmploymentType> employment = employmentTypeServices.findById("FTE");
        assertEquals("FTE", employment.get().getEmploymentType());
        assertEquals(60, employment.get().getNoticePeriod());
    }

    public static List<EmploymentType> getEmploymentType() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/employmentTypeDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        EmploymentType employmentType = new Gson().fromJson(jsonObject.get("EmploymentTypeDetails"), EmploymentType.class);
        List<EmploymentType> employmentTypeList = new ArrayList<EmploymentType>();
        employmentTypeList.add(employmentType);
        EmploymentType newemploymentType = new Gson().fromJson(jsonObject.get("EmploymentTypeDetails"), EmploymentType.class);
        newemploymentType.setEmploymentType("STE");
        newemploymentType.setNoticePeriod(15);
        employmentTypeList.add(newemploymentType);
        return employmentTypeList;
    }
}
