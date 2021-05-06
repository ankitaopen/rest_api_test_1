package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService
{
    //creating a method which returns the user based on id
    public User getUserById(Integer id);

    public User saveUser(User user);
    //this is the method prototype

    //these methods will be implemented in class UserServiceImpl
}
