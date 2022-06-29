package com.db.cloudschool.employeefeedback.repositories;

import com.db.cloudschool.employeefeedback.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
}
