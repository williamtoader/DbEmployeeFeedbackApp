package com.db.cloudschool.employeefeedback.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class ErrorResponse {
    public ErrorResponse(String error) {
        this.error = error;
    }

    public ErrorResponse(String error, String extra) {
        this.error = error;
        this.extra = extra;
    }

    String error;
    @JsonProperty
    String extra;
}
