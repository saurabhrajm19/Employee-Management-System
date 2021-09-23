package com.employee.employeemanagementsystem.servicetest;

import com.employee.employeemanagementsystem.entities.EmploymentType;
import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.UserDetailsRepository;
import com.employee.employeemanagementsystem.services.UserDetailsServices;
import com.google.gson.*;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServicesTest {

    @InjectMocks
    private UserDetailsServices userDetailsServices;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Test
    public void TestfindAll() throws IOException, NotFoundException, NullPointerException {
        when(userDetailsRepository.findAll()).thenReturn(getUserDetails());
        List<UserDetails> userDetailsList = userDetailsServices.findAll();
        assertEquals(2, userDetailsList.size());
        assertEquals("Saurabh", userDetailsList.get(0).getFirstName());
        assertEquals("Kristin", userDetailsList.get(1).getFirstName());
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
}
