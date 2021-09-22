package com.employee.employeemanagementsystem.servicetest;

import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.UserDetailsRepository;
import com.employee.employeemanagementsystem.services.UserDetailsServices;
import com.google.gson.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServicesTest {

    @InjectMocks
    private UserDetailsServices userDetailsServices;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Test
    public void TestfindAll() throws IOException, NotFoundException, NullPointerException {
        System.out.println("yo yoyoyoyoyo\n");
        when(userDetailsRepository.findAll()).thenReturn(getUserDetails());
        System.out.println("yo yoyoyoyoyo\n");
        List<UserDetails> userDetailsList = userDetailsServices.findAll();

        System.out.println(userDetailsList);
    }

    public static List<UserDetails> getUserDetails() throws IOException {
        System.out.println("yoyo test");
        String file = "src/test/java/com/employee/employeemanagementsystem/constants/userDetails.json";
        String reader = new String(Files.readAllBytes(Paths.get(file)));
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        UserDetails userDetails = new Gson().fromJson(jsonObject.get("UserDetails"), UserDetails.class);
        List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.add(userDetails);
        System.out.println("User added\n"+userDetailsList);
        //UserDetails newUserDetails = new Gson().fromJson(jsonObject.get("UserDetails"), UserDetails.class);
        UserDetails newUserDetails = userDetails;
        newUserDetails.setFirstName("Kristin");
        newUserDetails.setLastName("Kreuk");
        userDetailsList.add(newUserDetails);
        System.out.println("User added\n"+userDetailsList);
        return userDetailsList;
    }
}
