package com.project.UserService.controller;

import com.project.UserService.dto.UserRequest;
import com.project.UserService.dto.UserResponse;
import com.project.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.createUser(userRequest);
        return new ResponseEntity<>(userResponse , HttpStatus.CREATED);
    }

    @GetMapping("/GetUser/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id){
            UserResponse userResponse = userService.getUserById(id);
            return new ResponseEntity<>(userResponse , HttpStatus.OK);
    }

    @GetMapping("/getUserByEmailId/{email}")
    public ResponseEntity<UserResponse> getUserByEmailId(@PathVariable("email") String Email){
        UserResponse userResponse = userService.getUserByEmailId(Email);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id , @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.updateUser(id,userRequest);
        return new ResponseEntity<>(userResponse , HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
    userService.deleteUser(id);
        return new ResponseEntity<>("User Deactivated Succesfully", HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> userResponses = userService.getAllUsers();


        return new ResponseEntity<>(userResponses , HttpStatus.OK);
    }













}
