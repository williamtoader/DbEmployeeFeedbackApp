package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.dto.user.UserCreationInput;
import com.db.cloudschool.employeefeedback.model.User;
import com.db.cloudschool.employeefeedback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@SchemaMapping(typeName = "User")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @QueryMapping("User")
    public User getUser(@Argument Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @QueryMapping("Users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @MutationMapping("CreateUser")
    public User createUser(@Argument("input") UserCreationInput input) {
        return userRepository.save(User.builder()
            .firstName(input.getFirstName())
            .lastName(input.getLastName())
            .email(input.getEmail())
            .build()
        );
    }
}
