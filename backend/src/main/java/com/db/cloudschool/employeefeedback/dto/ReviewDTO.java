package com.db.cloudschool.employeefeedback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {
    private Double score1;
    private Double score2;
    private Double score3;
    private String comment;
}
