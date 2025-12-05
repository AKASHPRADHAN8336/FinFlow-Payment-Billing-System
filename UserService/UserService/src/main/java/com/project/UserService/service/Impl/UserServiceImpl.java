package com.project.UserService.service.Impl;

import com.project.UserService.dto.UserRequest;
import com.project.UserService.dto.UserResponse;
import com.project.UserService.mapper.UserToUserResponse;
import com.project.UserService.mapper.userRequestToUser;
import com.project.UserService.model.User;
import com.project.UserService.repository.UserRepository;
import com.project.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = userRequestToUser.UserReqToUser(userRequest);
        User savedUser=userRepository.save(user);
        return UserToUserResponse.userToUserRes(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user=userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found" + id));



        return UserToUserResponse.userToUserRes(user);

    }

    @Override
    public UserResponse getUserByEmailId(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cannot find the user" + email));

        return UserToUserResponse.userToUserRes(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);

    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserToUserResponse::userToUserRes)
                .collect(Collectors.toList());
    }
}
