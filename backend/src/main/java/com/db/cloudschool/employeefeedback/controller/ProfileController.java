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

    /**
     * This function will send a request to find
     * the profile with the specified id
     * @param id
     * @return the profile with the specified id, if found
     * @throws ProfileNotFoundException
     */
    @GetMapping("/{id}")
    public Profile getProfileById(@PathVariable Long id) throws ProfileNotFoundException {
        return profileService.getProfileById(id);
    }

    /**
     * This function will send a request to find all profiles
     * that have either the first name or the last name equal
     * to the one sent as an argument
     * @param name
     * @return an arraylist of profiles
     * @throws ProfileNotFoundException
     */
    @GetMapping("/name/{name}")
    public ArrayList<Profile> getProfilesByName(@PathVariable String name) throws ProfileNotFoundException {
        Set<Profile> profileSet = new HashSet<>();
        profileSet.addAll(profileService.getProfilesByFirstName(name));
        profileSet.addAll(profileService.getProfilesByLastName(name));
        return new ArrayList<>(profileSet);
    }

    /**
     * This function will send a request to find
     * all profiles with the specified full name
     * @param firstName
     * @param lastName
     * @return a list of profiles
     * @throws ProfileNotFoundException
     */
    @GetMapping("/fullname/{firstName}/{lastName}")
    public List<Profile> getProfilesByFullName(@PathVariable String firstName, @PathVariable String lastName) throws ProfileNotFoundException {
        return profileService.getProfilesByFullName(firstName, lastName);
    }

    /**
     * This function will send a request to register
     * a profile in the database
     * @param profile
     * @return the newly registered profile
     */
    @PostMapping("/register")
    public Profile register(@RequestBody Profile profile){
        profileService.register(profile);
        return profile;
    }

    /**
     * This function will send a request to update
     * a profile in the database
     * @param profile
     * @return the newly updated profile
     */
    @PostMapping("/update")
    public Profile update(@RequestBody Profile profile){
        profileService.update(profile);
        return profile;
    }

    /**
     * This function will send a request to remove
     * a profile from the database
     * @param id
     * @return the deleted profile
     * @throws ProfileNotFoundException
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) throws ProfileNotFoundException {
        profileService.deleteProfile(id);
    }
}