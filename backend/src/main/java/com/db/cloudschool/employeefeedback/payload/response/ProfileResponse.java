package com.db.cloudschool.employeefeedback.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ProfileResponse {
    @Data
    @Builder
    public static class ReceivedReview {
        Long reviewId;
        Long timestamp;
        String senderEmail;
        Long senderId;
        String comment;
        Scores scores;
    }

    @Data
    @Builder
    public static class Scores {
        Double score1;
        Double score2;
        Double score3;
    }

    Double score1;
    Double score2;
    Double score3;

    Long apiUserId;

    String biography;
    String firstName;
    String lastName;

    String emailAddress;

    String profilePhotoURL;

    List<ReceivedReview> receivedReviews;
}
