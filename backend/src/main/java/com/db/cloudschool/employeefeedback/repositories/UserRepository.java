package com.db.cloudschool.employeefeedback.repositories;

import com.db.cloudschool.employeefeedback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
