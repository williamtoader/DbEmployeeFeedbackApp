package com.db.cloudschool.employeefeedback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue
    private Long id;

    private String biography;

    private String firstName;
    private String lastName;
    private String profilePhotoURL;

    /**
     * Mean score for all reviews based upon review policy.
     */
    @Transient
    private Double computedScore1;
    @Transient
    private Double computedScore2;
    @Transient
    private Double computedScore3;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "receiver")
    List<Review> receivedReviews;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "sender")

    List<Review> sentReviews;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "identity_id", nullable = false, referencedColumnName = "id")
//    @MapsId
    Identity identity;
}
