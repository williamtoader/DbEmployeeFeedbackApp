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
    private Integer computedScore1;
    private Integer computedScore2;
    private Integer computedScore3;

    @OneToMany
    List<Review> receivedReviews;

    @OneToMany
    List<Review> sentReviews;

    @OneToOne
    Identity identity;
}
