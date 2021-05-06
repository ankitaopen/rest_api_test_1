package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

@RestController
//this class is annotated with rest controller as we are creating restful web services
public class UserController
{
    @Autowired
    private UserService userService;
    //creating the GET request API
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id)
    {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
    //creating the POST request API
    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User user)
    {
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
        //here the HTTP status code is returned as 201
    }


}
