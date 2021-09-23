package com.employee.employeemanagementsystem.servicetest;

import com.employee.employeemanagementsystem.entities.*;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.BusinessUnitRepository;
import com.employee.employeemanagementsystem.repository.EmploymentTypeRepository;
import com.employee.employeemanagementsystem.services.BusinessUnitServices;
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
public class BusinessUnitServicesTest {
    @InjectMocks
    private BusinessUnitServices businessUnitServices;

    @Mock
    private BusinessUnitRepository businessUnitRepository;

    @Test
    public void findAllTest() throws IOException, NotFoundException {
        when(businessUnitRepository.findAll()).thenReturn(getBusinessUnit());
        List<BusinessUnit> businessUnitList = businessUnitServices.findAll();
        assertEquals(2, businessUnitList.size());
        assertEquals("FSI", businessUnitList.get(0).getBuName());
        assertEquals("ITS", businessUnitList.get(1).getBuName());
        assertEquals("Anonymous", businessUnitList.get(0).getDirectorDelivery());
        assertEquals("Mr. Nobody", businessUnitList.get(1).getDirectorDelivery());
    }

    @Test
    public void findByBuNameTest() throws IOException, NotFoundException {
        BusinessUnit businessUnit = getBusinessUnit().get(0);
        when(businessUnitRepository.findByBuName("FSI")).thenReturn(businessUnit);
        BusinessUnit business = businessUnitServices.findByBuName("FSI");
        assertEquals("FSI", business.getBuName());
        assertEquals("Anonymous", business.getDirectorDelivery());
        assertEquals("Qwerty", business.getDirectorTechnology());
    }

    public static List<BusinessUnit> getBusinessUnit() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/businessUnitDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        BusinessUnit businessUnit = new Gson().fromJson(jsonObject.get("BusinessUnitDetails"), BusinessUnit.class);
        List<BusinessUnit> businessUnitList = new ArrayList<BusinessUnit>();
        businessUnitList.add(businessUnit);
        BusinessUnit newbusinessUnit = new Gson().fromJson(jsonObject.get("BusinessUnitDetails"), BusinessUnit.class);
        newbusinessUnit.setBuName("ITS");
        newbusinessUnit.setDirectorDelivery("Mr. Nobody");
        businessUnitList.add(newbusinessUnit);
        return businessUnitList;
    }

}
