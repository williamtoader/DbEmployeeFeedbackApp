package com.db.cloudschool.employeefeedback.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO Check structure
@Data
public class ReviewDTO {
    private Double score1;
    private Double score2;
    private Double score3;
    private String comment;
}
