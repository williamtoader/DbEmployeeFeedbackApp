package com.db.cloudschool.employeefeedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class EmployeefeedbackApplication {
    /**
     * It starts Spring Boot Application.
     * @param args Array o strings: command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EmployeefeedbackApplication.class, args);
    }

}
