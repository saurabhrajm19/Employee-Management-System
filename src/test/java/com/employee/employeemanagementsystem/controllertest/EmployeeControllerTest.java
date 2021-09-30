package com.employee.employeemanagementsystem.controllertest;

import com.employee.employeemanagementsystem.controller.EmployeeController;
import com.employee.employeemanagementsystem.entities.Employee;
import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.services.EmployeeServices;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServices employeeServices;

    @Test
    public void getAllEmployeesTest() throws Exception {
        List<Employee> employeeList = getEmployee();
        when(employeeServices.findAll()).thenReturn(employeeList);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/employees/")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(result.getResponse().toString().isEmpty());
    }

    @Test
    public void resignNoticeTest() throws Exception {
        when(employeeServices.resignNotice("HOP-2021-FTE-0001")).thenReturn("Noticed");
        RequestBuilder request = MockMvcRequestBuilders
                .get("/employees/notice/HOP-2021-FTE-0001")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        //System.out.println(result.getResponse()+"\n"+result.getResponse().getContentAsString()+"\n"+result.toString()+"\n"+result.getResponse().toString()+"\n"+result.getResponse().getHeaderNames());
        assertFalse(result.getResponse().toString().isEmpty());
        assertEquals("Noticed", result.getResponse().getContentAsString());
    }

    @Test
    public void getEmployeeDetailsTest() throws Exception {
        Employee employee = getEmployeeContent();
        when(employeeServices.fetchEmployeeDetails("HOP-2021-FTE-0001")).thenReturn(employee.toString());
        RequestBuilder request = MockMvcRequestBuilders
                .get("/employees/HOP-2021-FTE-0001")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(result.getResponse().toString().isEmpty());
    }

    @Test
    public void onboardedOnDateTest() throws Exception {
        List<Employee> employeeList = getEmployee();
        when(employeeServices.onboardedOnDate("2021-08-22")).thenReturn(employeeList);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/employees/onboard/2021-08-22")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(result.getResponse().toString().isEmpty());
    }

    @Test
    public void completesNoticePeriodTest() throws Exception {
        List<Employee> employeeList = getEmployee();
        when(employeeServices.completesNoticePeriod("2021-08-22")).thenReturn(employeeList);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/employees/completes-notice/2021-08-22")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(result.getResponse().toString().isEmpty());
    }

    @Test
    public void updateJobRoleTest() throws Exception {
        when(employeeServices.updateJobRole("HOP-2021-FTE-0001","SDE-2")).thenReturn("SDE-2");
        String requestJson = getUpdatedJobRole();
        RequestBuilder request = MockMvcRequestBuilders
                .put("/employees/update-jobRole/")
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(result.getResponse().toString().isEmpty());
        assertEquals("SDE-2", result.getResponse().getContentAsString());
    }

    @Test
    public void assignBusinessUnitTest() throws Exception {
        when(employeeServices.assignBusinessUnit("HOP-2021-FTE-0001","FSI")).thenReturn("FSI");
        String requestJson = getBU();
        RequestBuilder request = MockMvcRequestBuilders
                .put("/employees/assign-bu/")
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(result.getResponse().toString().isEmpty());
        assertEquals("FSI", result.getResponse().getContentAsString());
    }




    public static List<Employee> getEmployee() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/employeeDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        Employee employee = new Gson().fromJson(jsonObject.get("EmployeeDetails"), Employee.class);
        List<Employee> employeeList = new ArrayList<Employee>();
        employeeList.add(employee);
        Employee newEmployee = new Gson().fromJson(jsonObject.get("EmployeeDetails"), Employee.class);
        newEmployee.setFirstName("Kristin");
        newEmployee.setLastName("Kreuk");
        employeeList.add(newEmployee);
        return employeeList;
    }

    public static Employee getEmployeeContent() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/employeeDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        Employee employee = new Gson().fromJson(jsonObject.get("EmployeeDetails"), Employee.class);
        return employee;
    }

    public String getUpdatedJobRole() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/updateJobRole.json";
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public String getBU() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/assignBU.json";
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
