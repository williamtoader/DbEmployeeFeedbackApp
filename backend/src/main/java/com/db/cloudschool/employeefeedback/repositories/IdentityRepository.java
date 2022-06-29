package com.db.cloudschool.employeefeedback.repositories;

import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityRepository extends JpaRepository<Identity, Long> {
    Identity findByEmail(Email email);
    Identity findByEmail_Address(String emailAddress);
    Identity findByProfile(Profile profile);

    void deleteByEmail_Address(String emailAddress);
}
