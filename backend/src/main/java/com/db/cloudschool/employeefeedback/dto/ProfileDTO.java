package com.db.cloudschool.employeefeedback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProfileDTO {
    @JsonProperty()
    String firstName;
    @JsonProperty()
    String lastName;
    @JsonProperty()
    String biography;
}
