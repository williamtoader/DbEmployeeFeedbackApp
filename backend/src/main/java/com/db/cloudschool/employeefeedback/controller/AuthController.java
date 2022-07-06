package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.dto.LoginDTO;
import com.db.cloudschool.employeefeedback.dto.RegisterDTO;
import com.db.cloudschool.employeefeedback.exceptions.EmailAddressNotConfirmedException;
import com.db.cloudschool.employeefeedback.exceptions.IdentityNotFoundException;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.payload.request.AccessTokenRequest;
import com.db.cloudschool.employeefeedback.payload.response.AccessTokenResponse;
import com.db.cloudschool.employeefeedback.payload.response.RefreshTokenResponse;
import com.db.cloudschool.employeefeedback.security.decorator.AuthenticationStatusToken;
import com.db.cloudschool.employeefeedback.security.service.CredentialsService;
import com.db.cloudschool.employeefeedback.service.RegistrationService;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequiredArgsConstructor
public class AuthController {
    final RegistrationService registrationService;
    final CredentialsService credentialsService;


    @PostMapping(path = "/login")
    public RefreshTokenResponse login(@RequestBody LoginDTO loginDTO){
        if(credentialsService.validatePassword(
                loginDTO.getEmailAddress(),
                loginDTO.getPassword()
        )) {
            return new RefreshTokenResponse(credentialsService.generateRefreshToken(loginDTO.getEmailAddress()));
        }
        else {
            throw new BadCredentialsException("Login fail");
        }
    }

//    @RequestMapping(path = "/register", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PostMapping("/register")
    public RefreshTokenResponse register(@RequestBody RegisterDTO data)
            throws EmailAddressNotConfirmedException, IdentityNotFoundException, MailjetException {
        registrationService.registerIdentity(data);
        return new RefreshTokenResponse(credentialsService.generateRefreshToken(data.getEmailAddress()));
    }

    @PostMapping(path = "/auth/accesstoken")
    public AccessTokenResponse getAccessToken(@RequestBody AccessTokenRequest accessTokenRequest) throws EmailAddressNotConfirmedException, IdentityNotFoundException {
        Identity identity = credentialsService.validateRefreshToken(accessTokenRequest.getRefreshToken());

        return new AccessTokenResponse(credentialsService.generateAccessToken(identity.getEmail().getAddress()));
    }

    @PostMapping(path = "/auth/invalidaterefreshtokens")
    public void invalidateAccessToken(Authentication authentication){
        if(authentication.isAuthenticated() && authentication instanceof AuthenticationStatusToken) {
            Identity identity = ((AuthenticationStatusToken)authentication).getPrincipal();
            credentialsService.resetRefreshTokenSignatureKey(identity.getEmail().getAddress());
        }
    }


    @PostMapping(path = "/auth/test")
    public Identity test(Authentication authentication) throws Exception {
        if(authentication.isAuthenticated() && authentication instanceof AuthenticationStatusToken) {
            return ((AuthenticationStatusToken)authentication).getPrincipal();
        }
        else {
            throw new Exception("Error");
        }
    }
}
