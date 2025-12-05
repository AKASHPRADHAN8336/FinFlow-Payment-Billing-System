package com.project.UserService.mapper;

import com.project.UserService.dto.UserRequest;
import com.project.UserService.dto.UserResponse;
import com.project.UserService.model.User;


public class UserToUserResponse {


    public static UserResponse userToUserRes(User user){
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getDob(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()

        );

        System.out.println(userResponse.getFirstName());
        System.out.println("-----------------------------------*******************_______________________");


        return userResponse;
    }
}
