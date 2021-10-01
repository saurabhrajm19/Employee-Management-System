package com.employee.employeemanagementsystem.controllertest;

import com.employee.employeemanagementsystem.controller.BusinessUnitController;
import com.employee.employeemanagementsystem.entities.BusinessUnit;
import com.employee.employeemanagementsystem.services.BusinessUnitServices;
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
@WebMvcTest(controllers = BusinessUnitController.class)
public class BusinessUnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessUnitServices businessUnitServices;

    @Test
    public void getAllBusinessUnitsTest() throws Exception {
        List<BusinessUnit> businessUnitList = new ArrayList<>();
        businessUnitList.add(getBusinessUnit());
        when(businessUnitServices.findAll()).thenReturn(businessUnitList);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/BusinessUnit/")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(result.getResponse().toString().isEmpty());
    }

    @Test
    public void createBusinessUnitTest() throws Exception {
        String businessUnitContent = getBUContent();
        doNothing().when(businessUnitServices).save(getBusinessUnit());
        RequestBuilder request = MockMvcRequestBuilders
                .post("/BusinessUnit/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(businessUnitContent);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    public static BusinessUnit getBusinessUnit() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/businessUnitDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        return new Gson().fromJson(jsonObject.get("BusinessUnitDetails"), BusinessUnit.class);
    }

    public static String getBUContent() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/businessUnitDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        return jsonObject.get("BusinessUnitDetails").toString();
    }
}
