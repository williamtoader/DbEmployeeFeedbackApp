package com.db.cloudschool.employeefeedback.service;

import com.db.cloudschool.employeefeedback.dto.RegisterDTO;
import com.db.cloudschool.employeefeedback.exceptions.EmailAddressNotConfirmedException;
import com.db.cloudschool.employeefeedback.exceptions.IdentityNotFoundException;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.model.Profile;
import com.db.cloudschool.employeefeedback.repositories.EmailRepository;
import com.db.cloudschool.employeefeedback.repositories.IdentityRepository;
import com.db.cloudschool.employeefeedback.repositories.ProfileRepository;
import com.db.cloudschool.employeefeedback.security.service.CredentialsService;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This service handles the user registration process.
 */
@RequiredArgsConstructor
@Service
public class RegistrationService {
    public final IdentityRepository identityRepository;
    public final EmailRepository emailRepository;
    public final EmailConfirmationService emailConfirmationService;
    public final CredentialsService credentialsService;
    public final ProfileService profileService;
    public final ProfileRepository profileRepository;

    /*
     * Register a new user.
     *
     * @param onboardRegisterDTO The user data.
     * @return The user profile.
     * @throws MailjetException if the email could not be sent.
     */
    public Identity registerIdentity(RegisterDTO dto) throws MailjetException, EmailAddressNotConfirmedException, IdentityNotFoundException {
        //TODO: Create credentials
        // Add user role

        Identity identity =  identityRepository.save(
                Identity.builder()
                        .apiUserId(null)
                        .roles(new HashSet<>(Set.of("user")))
                        .build()
        );
        identity.setProfile(profileRepository.save(
                Profile.builder()
                        .biography(dto.getBiography())
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .profilePhotoURL(
                                String.format(
                                        "https://robohash.org/%s",
                                        dto.getEmailAddress())
                        )
                        .identity(identity)
                        .build()));
        identity.setCredentials(
                credentialsService.addCredentials(
                        credentialsService.generateCredentials(
                                dto.getEmailAddress(),
                                dto.getPassword(),
                                identity
                        )
                )
        );
        identity.setEmail(
                emailConfirmationService.createNewEmail(
                        dto.getEmailAddress(),
                        identity
                )
        );

        return identityRepository.saveAndFlush(identity);
    }
}
