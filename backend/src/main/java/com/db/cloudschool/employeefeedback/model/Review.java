package com.db.cloudschool.employeefeedback.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long reviewId;

    @ManyToOne
    private Profile sender;

    @ManyToOne
    private Profile receiver;

    @Min(10)
    @Max(50)
    private Double score1;

    @Min(10)
    @Max(50)
    private Double score2;

    @Min(10)
    @Max(50)
    private Double score3;

    private String comment;
}