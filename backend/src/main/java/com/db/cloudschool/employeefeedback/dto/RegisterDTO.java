package com.db.cloudschool.employeefeedback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class RegisterDTO {
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
    @JsonProperty()
    String profilePhoto;
    @JsonProperty()
    String biography;

}
