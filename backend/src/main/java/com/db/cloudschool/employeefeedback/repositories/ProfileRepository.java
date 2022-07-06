package com.db.cloudschool.employeefeedback.repositories;

import com.db.cloudschool.employeefeedback.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Profile findById(Long id);
    List<Profile> getProfilesByFirstName(String firstName);
    List<Profile> getProfilesByLastName(String lastName);

}