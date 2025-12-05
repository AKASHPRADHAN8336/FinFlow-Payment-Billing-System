package com.project.UserService.service;

import com.project.UserService.dto.UserRequest;
import com.project.UserService.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(Long id);
    UserResponse getUserByEmailId(String email);

    UserResponse updateUser(Long id , UserRequest userRequest);

    void deleteUser(Long id);
    List<UserResponse> getAllUsers();
}
