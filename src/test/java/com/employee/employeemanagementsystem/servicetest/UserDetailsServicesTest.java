package com.employee.employeemanagementsystem.servicetest;

import com.employee.employeemanagementsystem.controller.UserDetailsController;
import com.employee.employeemanagementsystem.entities.UserDetails;
import com.employee.employeemanagementsystem.exceptions.NotFoundException;
import com.employee.employeemanagementsystem.repository.EmploymentTypeRepository;
import com.employee.employeemanagementsystem.repository.UserDetailsRepository;
import com.employee.employeemanagementsystem.services.EmployeeServices;
import com.employee.employeemanagementsystem.services.UserDetailsServices;
import com.google.gson.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@WebMvcTest(UserDetailsController.class)
//@SpringBootTest
public class UserDetailsServicesTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    UserDetailsRepository userDetailsRepository;

    @MockBean
    UserDetailsServices userDetailsServices;

    @MockBean
    UserDetails userDetails;

    @MockBean
    EmployeeServices employeeServices;

    @MockBean
    EmploymentTypeRepository employmentTypeRepository;

    @Test
    public void TestUserDetails() throws NotFoundException {
        MockitoAnnotations.openMocks(this);
        //MyRestController controller = new MyRestController(employeeService);
        //mvc = MockMvcBuilders.standaloneSetup(controller).build();
        //LocalDate date = LocalDate.of(2020, 2, 13);
        JSONParser jsonParser = new JSONParser();
        System.out.println("Hi this is test");
        try
        {
            //FileReader reader = new FileReader("C:\\Users\\sauraraj\\Documents\\Employee management System\\employee-management-system\\src\\test\\java\\com\\employee\\employeemanagementsystem\\constants\\userDetails.json");
            //Read JSON file
            //Object obj = jsonParser.parse(reader);
            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            //JSONObject jsonObject = (JSONObject) obj;
            String file = "C:\\Users\\sauraraj\\Documents\\Employee management System\\employee-management-system\\src\\test\\java\\com\\employee\\employeemanagementsystem\\constants\\userDetails.json";
            String reader = readFileAsString(file);
            JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
            userDetails = new Gson().fromJson(jsonObject.get("UserDetails"), UserDetails.class);
            userDetailsRepository.save(userDetails);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("yo yoyoyoyoyo\n"+userDetailsRepository.findAll());

    }
    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
