package com.employee.employeemanagementsystem.controllertest;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.employee.employeemanagementsystem.controller.UserDetailsController;
import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.services.UserDetailsServices;
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
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserDetailsController.class)
public class UserDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServices userDetailsServices;

    @Test
    public void getAllUserDetailsTest() throws Exception {
        List<UserDetails> userDetailsList = getUserDetails();
        when(userDetailsServices.findAll()).thenReturn(userDetailsList);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/userDetails/")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertFalse(result.getResponse().toString().isEmpty());
    }

    @Test
    public void createUserDetailsTest() throws Exception {
        String userContent = getUserContent();
        UserDetails userDetails = new UserDetails();
        doNothing().when(userDetailsServices).save(userDetails);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/userDetails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userContent);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Done", result.getResponse().getContentAsString());
    }



    @Test
    public void onBoardSTE() throws Exception {
        String userContent = getUserContent();
        UserDetails userDetails = new UserDetails();
        doNothing().when(userDetailsServices).save(userDetails);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/userDetails/onBoardSTE")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userContent);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Done", result.getResponse().getContentAsString());
    }

    @Test
    public void onBoardFTE() throws Exception {
        String userContent = getUserContent();
        UserDetails userDetails = new UserDetails();
        doNothing().when(userDetailsServices).save(userDetails);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/userDetails/onBoardFTE")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userContent);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Done", result.getResponse().getContentAsString());
    }

    @Test
    public void onBoardIntern() throws Exception {
        String userContent = getUserContent();
        UserDetails userDetails = new UserDetails();
        doNothing().when(userDetailsServices).save(userDetails);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/userDetails/onBoardIntern")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userContent);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Done", result.getResponse().getContentAsString());
    }

    public static List<UserDetails> getUserDetails() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/userDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        UserDetails userDetails = new Gson().fromJson(jsonObject.get("UserDetails"), UserDetails.class);
        List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.add(userDetails);
        UserDetails newUserDetails = new Gson().fromJson(jsonObject.get("UserDetails"), UserDetails.class);
        newUserDetails.setFirstName("Kristin");
        newUserDetails.setLastName("Kreuk");
        userDetailsList.add(newUserDetails);
        return userDetailsList;
    }

    public static String getUserContent() throws IOException {
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/userDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        return jsonObject.get("UserDetails").toString();
    }
}
