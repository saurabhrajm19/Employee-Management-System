package com.employee.employeemanagementsystem.controllertest;

import com.employee.employeemanagementsystem.controller.EmploymentTypeController;
import com.employee.employeemanagementsystem.entities.BusinessUnit;
import com.employee.employeemanagementsystem.entities.EmploymentType;
import com.employee.employeemanagementsystem.services.BusinessUnitServices;
import com.employee.employeemanagementsystem.services.EmploymentTypeServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmploymentTypeController.class)
public class EmploymentTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmploymentTypeServices employmentTypeServices;

    @Test
    public void getAllEmploymentTypesTest() throws Exception {
        List<EmploymentType> employmentTypeList = new ArrayList<>();
        employmentTypeList.add(getEmploymentType());
        when(employmentTypeServices.findAll()).thenReturn(employmentTypeList);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/employment/")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(result.getResponse().toString().isEmpty());
    }

    @Test
    public void createBusinessUnitTest() throws Exception {
        String employmentTypeContent = getEmploymentTypeContent();
        doNothing().when(employmentTypeServices).save(getEmploymentType());
        RequestBuilder request = MockMvcRequestBuilders
                .post("/employment/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employmentTypeContent);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    public static EmploymentType getEmploymentType() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/employmentTypeDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        return new Gson().fromJson(jsonObject.get("EmploymentTypeDetails"), EmploymentType.class);
    }

    public static String getEmploymentTypeContent() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/employmentTypeDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        return jsonObject.get("EmploymentTypeDetails").toString();
    }
}
