package com.db.cloudschool.employeefeedback.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse implements Serializable {
    Long reviewId;
    String message;
    String error;
}
