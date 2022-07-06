package com.db.cloudschool.employeefeedback.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileListingResponse {
    Long apiUserId;
    String emailAddress;
    String firstName;
    String lastName;
    String profilePhotoURL;
}
