package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.dto.ReviewDTO;
import com.db.cloudschool.employeefeedback.exceptions.EmailAddressNotConfirmedException;
import com.db.cloudschool.employeefeedback.exceptions.IdentityNotFoundException;
import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.model.Profile;
import com.db.cloudschool.employeefeedback.payload.response.ReviewIdResponse;
import com.db.cloudschool.employeefeedback.security.decorator.AuthenticationStatusToken;
import com.db.cloudschool.employeefeedback.service.IdentityService;
import com.db.cloudschool.employeefeedback.service.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final IdentityService identityService;

    @PostMapping(path = "/id/{id}")
    public ReviewIdResponse addReviewForUserById(Authentication authentication, @PathVariable Long userId, @RequestBody ReviewDTO reviewDTO) throws Exception {
        if(authentication.isAuthenticated() && authentication instanceof AuthenticationStatusToken) {
            Profile sender = ((AuthenticationStatusToken) authentication).getPrincipal().getProfile();
            Profile receiver = identityService.getIdentity(userId).getProfile();
            return new ReviewIdResponse(reviewService.create(reviewDTO, sender, receiver).getReviewId());
        }
        else {
            throw new BadCredentialsException("Not authenticated");
        }
    }

    @PostMapping(path = "/email/{email}")
    public ReviewIdResponse addReviewForUserByEmail(Authentication authentication, @PathVariable String email, @RequestBody ReviewDTO reviewDTO) throws Exception {
        if(authentication.isAuthenticated() && authentication instanceof AuthenticationStatusToken) {
            Profile sender = ((AuthenticationStatusToken) authentication).getPrincipal().getProfile();
            Profile receiver = identityService.getIdentity(email).getProfile();
            return new ReviewIdResponse(reviewService.create(reviewDTO, sender, receiver).getReviewId());
        }
        else {
            throw new BadCredentialsException("Not authenticated");
        }
    }
}
