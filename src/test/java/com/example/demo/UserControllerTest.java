package com.example.demo;

//import com.example.demo.controller.UserController;
import com.example.demo.controllers.UserController;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static reactor.core.publisher.Mono.when;

//@RunWith(SpringRunner.class)
//if we are using junit 4 then we have to add this above annotation
@WebMvcTest(UserController.class)
//this annotation provides the name of the controller class which we are going to test
public class UserControllerTest
{
    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;
    //this is used for mocking the http request and response

    @MockBean
    private UserService userService;

    @Test
    public void getUserByIdTest() throws Exception {
        //TEST CASE to test the GET APIs
        //with this we can test the REST API which returns the user details based on given id
        //steps
        //1- mock the data returned by user service class

        User user = new User();
        //here we need to set up a user object for our mocked data
        user.setName("ankita");
        user.setEmail("ankita.sahoo@bankopen.co");
        user.setGender("female");
        user.setPhone("987654321");

        when(userService.getUserById(anyInt())).thenReturn(user);

        //2- create a mock HTTP request to verify the expected result
        mockMvc.perform(MockMvcRequestBuilders.get("/user/12"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ankita"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ankita.sahoo@bankopen.co"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("987654321"))
                .andExpect(status().isOk());


    }
    //TEST CASE to test the post APIs
    @Test
    public void saveUserTest() throws Exception {
        //steps
        //1- mock the used data that we have to save
        //here we are creating an object user and saving the following details
        User user = new User();
        user.setId(1);
        user.setName("ankita");
        user.setEmail("ankita.sahoo@bankopen.co");
        user.setGender("female");
        user.setPhone("987654321");
        //we have to mock the behaviour when the user service is trying to save the user details
        when(userService.saveUser(any(User.class))).thenReturn(user);

        //2- mock request "/user"
        //mocked post request to save the user details
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ankita"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ankita.sahoo@bankopen.co"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("987654321"));

                //we also have to pass this user object in the request body of POST api
                //here we expect the status to be isCreated, we will send the http code as 201 when the user is created
                //and whenever the user field is created we expect the id filed to be present in the response body

    }
}
