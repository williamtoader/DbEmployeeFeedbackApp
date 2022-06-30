package com.db.cloudschool.employeefeedback.repositories;

import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdentityRepository extends JpaRepository<Identity, Long> {
    Identity findByEmail(Email email);
    Identity findByEmail_Address(String emailAddress);
    Identity findByProfile(Profile profile);
    List<Identity> findAllByEmail_ConfirmedTrue();
    void deleteByEmail_Address(String emailAddress);

}
