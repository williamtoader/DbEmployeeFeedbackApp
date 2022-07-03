package com.db.cloudschool.employeefeedback.service;

import com.db.cloudschool.employeefeedback.dto.OnboardRegisterDTO;
import com.db.cloudschool.employeefeedback.exceptions.EmailAddressNotConfirmedException;
import com.db.cloudschool.employeefeedback.exceptions.IdentityNotFoundException;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.model.Profile;
import com.db.cloudschool.employeefeedback.repositories.EmailRepository;
import com.db.cloudschool.employeefeedback.repositories.IdentityRepository;
import com.db.cloudschool.employeefeedback.security.service.CredentialsService;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This service handles the user registration process.
 */
@RequiredArgsConstructor
@Service
public class OnboardRegisterService {
    public final IdentityRepository identityRepository;
    public final EmailRepository emailRepository;
    public final EmailConfirmationService emailConfirmationService;
    public final CredentialsService credentialsService;

    /*
     * Register a new user.
     *
     * @param onboardRegisterDTO The user data.
     * @return The user profile.
     * @throws MailjetException if the email could not be sent.
     */
    public Identity registerIdentity(OnboardRegisterDTO dto) throws MailjetException, EmailAddressNotConfirmedException, IdentityNotFoundException {
        //TODO: Create credentials
        // Add user role
        Identity identity =  identityRepository.save(
                Identity.builder()
                        .apiUserId(null)
                        .email(
                                emailConfirmationService.createNewEmail(
                                        dto.getEmailAddress()
                                )
                        )
                        .profile(Profile.builder()
                                .biography(dto.getBiography())
                                .firstName(dto.getFirstName())
                                .lastName(dto.getLastName())
                                .profilePhotoURL(String.format("https://robohash.org/%s", dto.getEmailAddress()))
                                .build())
                        .build()
        );
        credentialsService.addCredentials(
                credentialsService.generateCredentials(
                        dto.getEmailAddress(),
                        dto.getPassword()
                )
        );
        return identity;
    }
}
