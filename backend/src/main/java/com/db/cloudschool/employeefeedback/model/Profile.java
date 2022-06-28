package com.db.cloudschool.employeefeedback.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {
    @Id
    private Long profileId;

    private String biography;

    private String firstName;
    private String lastName;
    private String profilePhotoURL;

    /**
     * Mean score for all reviews based upon review policy.
     */
    @Transient
    private Double computedScore1;
    private Double computedScore2;
    private Double computedScore3;

    @OneToMany
    List<Review> receivedReviews;

    @OneToMany
    List<Review> sentReviews;

    @OneToOne
    Identity identity;
}
