package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.model.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfileController {
    //TODO: inject service here and refactor

    @GetMapping(path = "/profiles")
    public List<Profile> getAll(){
        return null;
    }

    @GetMapping(path = "/profile/id/{id}")
    public Profile getById(@PathVariable Long profileId){
        return null;
    }

    @GetMapping(path = "/profile/email/{email}")
    public Profile getByEmail(@PathVariable Email profileEmail){
        return null;
    }

    @GetMapping(path = "/profile/personal")
    public Profile getByEmail(){
        return null;
    }

    @PostMapping(path = "/profile/personal/details")
    public void setProfileDetails(@RequestBody ProfileDTO profileDTO){
    }

    @PostMapping(path = "/profile/personal/photo")
    public void setProfileDetails(@RequestBody PhotoDTO photoDTO){
    }

}
