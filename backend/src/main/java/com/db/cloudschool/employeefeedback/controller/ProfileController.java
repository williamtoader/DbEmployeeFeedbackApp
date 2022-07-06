package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.dto.ProfileDTO;
import com.db.cloudschool.employeefeedback.exceptions.EmailAddressNotConfirmedException;
import com.db.cloudschool.employeefeedback.exceptions.IdentityNotFoundException;
import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.model.Profile;
import com.db.cloudschool.employeefeedback.payload.response.MessageResponse;
import com.db.cloudschool.employeefeedback.payload.response.ProfileListingResponse;
import com.db.cloudschool.employeefeedback.payload.response.ProfileResponse;
import com.db.cloudschool.employeefeedback.security.decorator.AuthenticationStatusToken;
import com.db.cloudschool.employeefeedback.service.IdentityService;
import com.db.cloudschool.employeefeedback.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final IdentityService identityService;
    private final ProfileService profileService;

    @GetMapping(path = "/profiles")
    public List<ProfileListingResponse> getAll(){
        return identityService.getAllIdentities().stream()
                .map(identity -> ProfileListingResponse
                        .builder()
                        .apiUserId(identity.getApiUserId())
                        .emailAddress(identity.getEmail().getAddress())
                        .profilePhotoURL(identity.getProfile().getProfilePhotoURL())
                        .firstName(identity.getProfile().getFirstName())
                        .lastName(identity.getProfile().getLastName())
                        .build()).collect(Collectors.toList());
    }

    @GetMapping(path = "/profile/id/{id}")
    public ProfileResponse getById(@PathVariable Long id) throws EmailAddressNotConfirmedException, IdentityNotFoundException {
        Identity identity = identityService.getIdentity(id);
        if(identity == null) throw new IdentityNotFoundException();
        return getProfileResponse(identity);
    }

    @GetMapping(path = "/profile/email/{email}")
    public ProfileResponse getByEmail(@PathVariable String email) throws EmailAddressNotConfirmedException, IdentityNotFoundException {
        Identity identity = identityService.getIdentity(email);
        if(identity == null) throw new IdentityNotFoundException();
        return getProfileResponse(identity);
    }

    @Transactional
    public ProfileResponse getProfileResponse(@NotNull Identity identity) {
        return ProfileResponse
                .builder()
                .apiUserId(identity.getApiUserId())
                .emailAddress(identity.getEmail().getAddress())
                .profilePhotoURL(identity.getProfile().getProfilePhotoURL())
                .firstName(identity.getProfile().getFirstName())
                .lastName(identity.getProfile().getLastName())
                .biography(identity.getProfile().getBiography())
                .score1(profileService.computeScores(
                        identity.getProfile().getReceivedReviews(),
                        1
                ) / 10)
                .score2(profileService.computeScores(
                        identity.getProfile().getReceivedReviews(),
                        2
                ) / 10)
                .score3(profileService.computeScores(
                        identity.getProfile().getReceivedReviews(),
                        3
                ) / 10)
                .receivedReviews(
                        identity.getProfile().getReceivedReviews().stream()
                                .map(review -> {
                                    return ProfileResponse.ReceivedReview
                                            .builder()
                                            .comment(review.getComment())
                                            .reviewId(review.getReviewId())
                                            .senderEmail(review.getSender().getIdentity().getEmail().getAddress())
                                            .senderId(review.getSender().getIdentity().getApiUserId())
                                            .timestamp(review.getTimestamp())
                                            .scores(ProfileResponse.Scores
                                                    .builder()
                                                    .score1(review.getScore1() / 10)
                                                    .score2(review.getScore2() / 10)
                                                    .score3(review.getScore3() / 10)
                                                    .build()
                                            )
                                            .build();
                                })
                                .collect(Collectors.toList())
                )
                .profilePhotoURL(identity.getProfile().getProfilePhotoURL())
                .build();
    }


    @GetMapping(path = "/profile/personal")
    @Transactional
    public ProfileResponse getPersonal(Authentication authentication){
        if(authentication.isAuthenticated() && authentication instanceof AuthenticationStatusToken) {
            return getProfileResponse(((AuthenticationStatusToken) authentication).getPrincipal());
        }
        else {
            throw new BadCredentialsException("Not authenticated");
        }
    }

    @PostMapping(path = "/profile/personal/details")
    public MessageResponse setProfileDetails(Authentication authentication, @RequestBody ProfileDTO profileDTO){
        if(authentication.isAuthenticated() && authentication instanceof AuthenticationStatusToken) {
            profileService.update(Profile
                            .builder()
                            .identity(((AuthenticationStatusToken) authentication).getPrincipal())
                            .biography(profileDTO.getBiography())
                            .firstName(profileDTO.getFirstName())
                            .lastName(profileDTO.getLastName())
                            .build()
            );
            return new MessageResponse("Updated details");
        }
        else {
            throw new BadCredentialsException("Not authenticated");
        }
    }

    @RequestMapping(path = "/profile/personal/photo", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public MessageResponse setProfilePhoto(Authentication authentication, @RequestPart MultipartFile profilePhoto){
        return new MessageResponse("Updated photo");
    }

}
