package com.db.cloudschool.employeefeedback.security.repository;

import com.db.cloudschool.employeefeedback.security.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String> {
}
