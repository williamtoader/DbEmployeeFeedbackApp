package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.payload.request.AccessTokenRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AuthController {
    //TODO: inject service here and refactor

    @PostMapping(path = "/login")
    public void login(@RequestBody LoginDTO loginDTO){
    }

    @RequestMapping(path = "/register", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void register(@RequestPart RegisterDTO data, @RequestPart MultipartFile profilePhoto){
    }

    @PostMapping(path = "/auth/access-token")
    public void getAccessToken(@RequestBody AccessTokenRequest accessTokenRequest){
    }

    @PostMapping(path = "/auth/invalid-date-refresh-token")
    public void invalidateAccessToken(){
    }
}
