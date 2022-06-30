package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.exceptions.ProfileNotFoundException;
import com.db.cloudschool.employeefeedback.model.Profile;
import com.db.cloudschool.employeefeedback.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("/{id}")
    public Profile getProfileById(@PathVariable Long id){
        return profileService.getProfileById(id);
    }

    @GetMapping("/{name}")
    public ArrayList<Profile> getProfilesByName(@PathVariable String name) throws ProfileNotFoundException {
        Set<Profile> profileSet = new HashSet<>();
        profileSet.addAll(profileService.getProfilesByFirstName(name));
        profileSet.addAll(profileService.getProfilesByLastName(name));
        return new ArrayList<>(profileSet);
    }

    @GetMapping("/{firstName}/{lastName}")
    public List<Profile> getProfilesByFullName(@PathVariable String firstName, @PathVariable String lastName) throws ProfileNotFoundException {
        return profileService.getProfilesByFullName(firstName, lastName);
    }

    @PostMapping("/register")
    public Profile register(@RequestBody Profile profile){
        profileService.register(profile);
        return profile;
    }

    @PostMapping("/update")
    public Profile update(@RequestBody Profile profile){
        profileService.update(profile);
        return profile;
    }

    @DeleteMapping("/delete/{id}")
    public Profile delete(@PathVariable Long id){
        Profile profile = profileService.getProfileById(id);
        profileService.deleteProfile(id);
        return profile;
    }
}