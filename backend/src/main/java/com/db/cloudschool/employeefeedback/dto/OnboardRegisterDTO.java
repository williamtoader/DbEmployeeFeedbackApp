package com.db.cloudschool.employeefeedback.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardRegisterDTO {
    String emailAddress;
    /**
     * Expecting plain text password
     */
    String password;
    String firstName;
    String lastName;
    /**
     * Expecting small png/jpeg file
     */
    MultipartFile profilePhoto;
    String biography;
}
