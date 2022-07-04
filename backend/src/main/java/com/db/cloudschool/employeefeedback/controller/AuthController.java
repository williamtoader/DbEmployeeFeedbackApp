package com.db.cloudschool.employeefeedback.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    //TODO: inject service here and refactor

    @PostMapping(path = "/login")
    public void login(@RequestBody CredentialsDTO credentialsDTO){
    }

    @PostMapping(path = "/register")
    public void register(@RequestBody CredentialsDTO credentialsDTO){
    }

    @PostMapping(path = "/auth/access-token")
    public void login(@RequestBody AccessTokenDTO accessTokenDTO){
    }

    @PostMapping(path = "/auth/invalid-date-refresh-token")
    public void login(){
    }
}
