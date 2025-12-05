package com.project.UserService.mapper;


import com.project.UserService.dto.UserRequest;
import com.project.UserService.model.User;


public class userRequestToUser {

    public static User UserReqToUser(UserRequest userRequest){
        User user = new User(
                userRequest.getId(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getPhone(),
                userRequest.getAddress(),
                userRequest.getDob()
        );

        return user;
    }

}
